package com.hwsafe.wx.controller;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hwsafe.wx.controller.enums.MessageEventEnum;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.api.WxConsts.MenuButtonType;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;

/**
 * 微信 接入 消息接受
 *
 */
@Slf4j
@RestController
@RequestMapping("/wx/portal/{appid}")
public class WxPortalController {
    @Autowired
    private WxMpService wxService;
    @Autowired
    private WxMpMessageRouter messageRouter;
    public static final String MSGTYPE_EVENT = "event";// 消息类型--事件
    public static final String MSGTYPE_TEXT = "text";// 消息类型--文本消息
    public static final String MESSAGE_SUBSCIBE = "subscribe";// 消息事件类型--订阅事件
    public static final String MESSAGE_UNSUBSCIBE = "unsubscribe";// 消息事件类型--取消订阅事件
    public static final String MESSAGE_LOCATION = "LOCATION";// 地理信息

    /**
     * @param appid
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return 验证
     */
    @GetMapping(produces = "text/plain;charset=utf-8")
    public String authGet(@PathVariable String appid,
            @RequestParam(name = "signature", required = false) String signature,
            @RequestParam(name = "timestamp", required = false) String timestamp,
            @RequestParam(name = "nonce", required = false) String nonce,
            @RequestParam(name = "echostr", required = false) String echostr) {
        // 数据表初始化公众号信息setMultiConfigStorages
        log.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp,
                nonce, echostr);
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(
                    String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }

        if (wxService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }
        return "非法请求";
    }

    /**
     * @param appid
     * @param requestBody
     * @param signature
     * @param timestamp
     * @param nonce
     * @param openid
     * @param encType
     * @param msgSignature
     * @return 接受用户输入信息
     * @throws WxErrorException
     * @throws MalformedURLException
     */
    @PostMapping(produces = "application/xml; charset=UTF-8")
    public String post(@PathVariable String appid,
            @RequestBody String requestBody,
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("openid") String openid,
            @RequestParam(name = "encrypt_type", required = false) String encType,
            @RequestParam(name = "msg_signature", required = false) String msgSignature)
            throws MalformedURLException, WxErrorException {
        log.info(
                "\n接收微信请求：[openid=[{}], [signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                openid, signature, encType, msgSignature, timestamp, nonce,
                requestBody);
        // 数据表初始化公众号信息setMultiConfigStorages
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(
                    String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }

        if (!wxService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        String out = null;
        WxMpXmlOutMessage outMessage = null;
        WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
        if (MSGTYPE_EVENT.equals(inMessage.getMsgType())) {
            if (MessageEventEnum.MESSAGE_SUBSCIBE.getCode()
                    .equals(inMessage.getEvent())) {// 处理订阅事件
                // 获取openid用户信息 保存数据库
                outMessage = subscribeForText(inMessage.getToUser(),
                        inMessage.getFromUser());
            } else if (MessageEventEnum.MESSAGE_UNSUBSCIBE.getCode()
                    .equals(inMessage.getEvent())) {// 处理取消订阅事件
                unsubscribe(inMessage.getToUser(), inMessage.getFromUser());
            } else if (MessageEventEnum.MESSAGE_LOCATION.getCode()
                    .equals(inMessage.getEvent())) {// 处理取消订阅事件
                unsubscribe(inMessage.getToUser(), inMessage.getFromUser());
            }
        } else {
            if (encType == null) {
                // 明文传输的消息
                outMessage = this.route(inMessage);
                if (outMessage == null) {
                    return "";
                }
                out = outMessage.toXml();
            } else if ("aes".equalsIgnoreCase(encType)) {
                // aes加密的消息
                inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody,
                        wxService.getWxMpConfigStorage(), timestamp, nonce,
                        msgSignature);
                log.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
                outMessage = this.route(inMessage);
                if (outMessage == null) {
                    return "";
                }
                out = outMessage
                        .toEncryptedXml(wxService.getWxMpConfigStorage());
            }

            log.debug("\n组装回复信息：{}", out);
            WxMpXmlOutTextMessage text = WxMpXmlOutTextMessage.TEXT()
                    .toUser(outMessage.getToUserName())
                    .fromUser(outMessage.getFromUserName())
                    .content("您发了一个文本消息,已帮您记录").build();
            out = text.toXml();
        }
        if (StringUtils.isBlank(out)) {
            WxMpXmlOutTextMessage text = WxMpXmlOutTextMessage.TEXT()
                    .toUser(inMessage.getFromUser())
                    .fromUser(inMessage.getToUser())
                    .content("您发了一个" + MessageEventEnum
                            .codes(inMessage.getEvent()).getDesc() + ",已帮您记录")
                    .build();
            out = text.toXml();
        }
        return out;
        // return out;
    }

    private WxMpXmlOutMessage route(WxMpXmlMessage message) {
        try {
            return this.messageRouter.route(message);
        } catch (Exception e) {
            log.error("路由消息时出现异常！", e);
        }

        return null;
    }

    /*
     * 响应订阅事件--回复文本消息
     */
    public static WxMpXmlOutTextMessage subscribeForText(String toUserName,
            String fromUserName) {
        return WxMpXmlOutTextMessage.TEXT().toUser(toUserName)
                .fromUser(fromUserName).content("欢迎关注，精彩内容不容错过！！！").build();
    }

    /*
     * 响应取消订阅事件
     */
    public static String unsubscribe(String toUserName, String fromUserName) {
        // TODO 可以进行取关后的其他后续业务处理
        System.out.println("用户：" + fromUserName + "取消关注~");
        return "";
    }

    public String menuCreateSample(String appid)
            throws WxErrorException, MalformedURLException {
        WxMenu menu = new WxMenu();
        WxMenuButton button1 = new WxMenuButton();
        button1.setType(MenuButtonType.CLICK);
        button1.setName("今日歌曲");
        button1.setKey("V1001_TODAY_MUSIC");

//        WxMenuButton button2 = new WxMenuButton();
//        button2.setType(WxConsts.BUTTON_MINIPROGRAM);
//        button2.setName("小程序");
//        button2.setAppId("wx286b93c14bbf93aa");
//        button2.setPagePath("pages/lunar/index.html");
//        button2.setUrl("http://mp.weixin.qq.com");

        WxMenuButton button3 = new WxMenuButton();
        button3.setName("菜单");

        menu.getButtons().add(button1);
//        menu.getButtons().add(button2);
        menu.getButtons().add(button3);

        WxMenuButton button31 = new WxMenuButton();
        button31.setType(MenuButtonType.VIEW);
        button31.setName("搜索");
        button31.setUrl("http://www.soso.com/");

        WxMenuButton button32 = new WxMenuButton();
        button32.setType(MenuButtonType.VIEW);
        button32.setName("视频");
        button32.setUrl("http://v.qq.com/");

        WxMenuButton button33 = new WxMenuButton();
        button33.setType(MenuButtonType.CLICK);
        button33.setName("赞一下我们");
        button33.setKey("V1001_GOOD");

        WxMenuButton button34 = new WxMenuButton();
        button34.setType(MenuButtonType.VIEW);
        button34.setName("获取用户信息");

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            URL requestURL = new URL(request.getRequestURL().toString());
            String url = this.wxService.switchoverTo(appid)
                    .oauth2buildAuthorizationUrl(
                            String.format("%s://%s/wx/redirect/%s/login",
                                    requestURL.getProtocol(),
                                    requestURL.getHost(), appid),
                            WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
            button34.setUrl(url);
        }

        button3.getSubButtons().add(button31);
        button3.getSubButtons().add(button32);
        button3.getSubButtons().add(button33);
        button3.getSubButtons().add(button34);

        this.wxService.switchover(appid);
        return this.wxService.getMenuService().menuCreate(menu);
    }
}