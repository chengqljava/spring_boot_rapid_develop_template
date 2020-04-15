package com.hwsafe.jpush;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONObject;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.*;

public class JpushClientUtil {
    // private final static String appKey = "673c8a75c0461804b18de6ec";
    // private final static String masterSecret = "ca6d32420bef3f752c6aea16";
    private static JPushClient jPushClient;
    private static JpushClientUtil jpushClientUtil;

    private JpushClientUtil(String appKey, String masterSecret) {
        jPushClient = new JPushClient(masterSecret, appKey);
    }

    public static JpushClientUtil getInstance(String appKey,
            String masterSecret) {
        if (null == jpushClientUtil) {
            jpushClientUtil = new JpushClientUtil(appKey, masterSecret);
            return jpushClientUtil;
        } else {
            return jpushClientUtil;
        }

    }

    /**
     * 推送给设备标识参数的用户
     * 
     * @param registrationId
     *            设备标识
     * @param notification_title
     *            通知内容标题
     * @param msg_title
     *            消息内容标题
     * @param msg_content
     *            消息内容
     * @param extrasparam
     *            扩展字段
     * @return 0推送失败，1推送成功
     */
    public int sendToRegistrationId(String registrationId,
            String notification_title, String msg_title, String msg_content,
            String extrasparam) {
        int result = 0;
        try {
            PushPayload pushPayload = buildPushObject_all_registrationId_alertWithTitle(
                    registrationId, notification_title, msg_title, msg_content,
                    extrasparam);
            System.out.println(pushPayload);
            PushResult pushResult = jPushClient.sendPush(pushPayload);
            System.out.println(pushResult);
            if (pushResult.getResponseCode() == 200) {
                result = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return result;
    }

    /**
     * 发送给所有安卓用户
     * 
     * @param notification_title
     *            通知内容标题
     * @param notification_content
     *            通知内容
     * @param msg_title
     *            消息内容标题
     * @param msg_content
     *            消息内容
     * @param userid
     *            用户id
     * @param extrasparam
     *            扩展字段
     * @return 0推送失败，1推送成功
     */
    public static int sendToAllAndroid(String notification_title,
            String notification_content, String msg_title, String msg_content,
            String extrasparam, String userid) {
        int result = 0;
        try {
            PushPayload pushPayload = buildPushObject_android_all_alertWithTitle(
                    notification_title, notification_content, msg_title,
                    msg_content, extrasparam, userid);
            System.out.println(pushPayload);
            PushResult pushResult = jPushClient.sendPush(pushPayload);
            System.out.println(pushResult);
            if (pushResult.getResponseCode() == 200) {
                result = 1;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    /**
     * 发送给所有IOS用户
     * 
     * @param notification_title
     *            通知内容标题
     * @param msg_title
     *            消息内容标题
     * @param msg_content
     *            消息内容
     * @param extrasparam
     *            扩展字段
     * @return 0推送失败，1推送成功
     */
    public int sendToAllIos(String notification_title, String msg_title,
            String msg_content, String extrasparam) {
        int result = 0;
        try {
            PushPayload pushPayload = buildPushObject_ios_all_alertWithTitle(
                    notification_title, msg_title, msg_content, extrasparam);
            System.out.println(pushPayload);
            PushResult pushResult = jPushClient.sendPush(pushPayload);
            System.out.println(pushResult);
            if (pushResult.getResponseCode() == 200) {
                result = 1;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    /**
     * 发送给所有用户
     * 
     * @param notification_title
     *            通知内容标题
     * @param msg_title
     *            消息内容标题
     * @param msg_content
     *            消息内容
     * @param extrasparam
     *            扩展字段
     * @return 0推送失败，1推送成功
     */
    public int sendToAll(String notification_title, String msg_title,
            String msg_content, String extrasparam) {
        int result = 0;
        try {
            PushPayload pushPayload = buildPushObject_android_and_ios(
                    notification_title, msg_title, msg_content, extrasparam,
                    null, null);
            System.out.println(pushPayload);
            PushResult pushResult = jPushClient.sendPush(pushPayload);
            System.out.println(pushResult);
            if (pushResult.getResponseCode() == 200) {
                result = 1;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    /**
     * 别名发送
     * 
     * @param notification_title
     *            通知内容标题
     * @param msg_title
     *            消息内容标题
     * @param msg_content
     *            消息内容
     * @param extrasparam
     *            扩展字段
     * @param alias
     *            别名
     * @return 0推送失败，1推送成功
     */
    public int sendToAllAlias(String notification_title, String msg_title,
            String msg_content, String extrasparam, String... alias) {
        int result = 0;
        try {
            PushPayload pushPayload = buildPushObject_android_and_ios(
                    notification_title, msg_title, msg_content, extrasparam,
                    Arrays.asList(alias), null);
            PushResult pushResult = jPushClient.sendPush(pushPayload);
            if (pushResult.getResponseCode() == 200) {
                result = 1;
            }
        } catch (Exception e) {

            // e.printStackTrace();
        }

        return result;
    }

    /**
     * 别名发送
     * 
     * @param notification_title
     *            通知内容标题
     * @param msg_title
     *            消息内容标题
     * @param msg_content
     *            消息内容
     * @param extrasparam
     *            扩展字段
     * @param alias
     *            别名
     * @return 0推送失败，1推送成功
     */
    public int sendToAllAlias(String notification_title, String msg_title,
            String msg_content, Map<String, Object> extrasparam,
            String... alias) {
        int result = 0;
        try {
            PushPayload pushPayload = buildPushObject_android_and_ios(
                    notification_title, msg_title, msg_content,
                    JSONObject.toJSONString(extrasparam), Arrays.asList(alias),
                    null);
            System.out.println(pushPayload);
            PushResult pushResult = jPushClient.sendPush(pushPayload);
            System.out.println(pushResult);
            if (pushResult.getResponseCode() == 200) {
                result = 1;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    /**
     * 标签发送
     * 
     * @param notification_title
     *            通知内容标题
     * @param msg_title
     *            消息内容标题
     * @param msg_content
     *            消息内容
     * @param extrasparam
     *            扩展字段
     * @param tags
     *            别名
     * @return 0推送失败，1推送成功
     */
    public int sendToAllTags(String notification_title, String msg_title,
            String msg_content, String extrasparam, String... tags) {
        int result = 0;
        try {
            PushPayload pushPayload = buildPushObject_android_and_ios(
                    notification_title, msg_title, msg_content, extrasparam,
                    null, Arrays.asList(tags));
            System.out.println(pushPayload);
            PushResult pushResult = jPushClient.sendPush(pushPayload);
            System.out.println(pushResult);
            if (pushResult.getResponseCode() == 200) {
                result = 1;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    /**
     * 标签发送
     * 
     * @param notification_title
     *            通知内容标题
     * @param msg_title
     *            消息内容标题
     * @param msg_content
     *            消息内容
     * @param extrasparam
     *            扩展字段
     * @param tags
     *            别名
     * @return 0推送失败，1推送成功
     */
    public int sendToAllTags(String notification_title, String msg_title,
            String msg_content, Map<String, Object> extrasparam,
            String... tags) {
        int result = 0;
        try {
            PushPayload pushPayload = buildPushObject_android_and_ios(
                    notification_title, msg_title, msg_content,
                    JSONObject.toJSONString(extrasparam), null,
                    Arrays.asList(tags));
            System.out.println(pushPayload);
            PushResult pushResult = jPushClient.sendPush(pushPayload);
            System.out.println(pushResult);
            if (pushResult.getResponseCode() == 200) {
                result = 1;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    /**
     * @param notification_title
     * @param msg_title
     * @param msg_content
     * @param extrasparam
     * @param alias
     * @param tags
     * @return
     */
    public PushPayload buildPushObject_android_and_ios(
            String notification_title, String msg_title, String msg_content,
            String extrasparam, List<String> alias, List<String> tags) {

        Builder builder = PushPayload.newBuilder();
        builder.setPlatform(Platform.android_ios());
        if (null != alias && alias.size() > 0) {
            builder.setAudience(
                    Audience.alias(alias.toArray(new String[alias.size()])));
        } else if (null != tags && tags.size() > 0) {
            builder.setAudience(
                    Audience.tag(tags.toArray(new String[tags.size()])));
        } else {
            builder.setAudience(Audience.all());
        }
        builder.setNotification(
                Notification.newBuilder().setAlert(notification_title)
                        .addPlatformNotification(AndroidNotification
                                .newBuilder().setAlert(notification_title)
                                .setTitle(notification_title)
                                // 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extra", extrasparam).build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                // 传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(notification_title)
                                // 直接传alert
                                // 此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                // 此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("sound.caf")
                                // 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extra", extrasparam)
                                // 此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                // .setContentAvailable(true)

                                .build())
                        .build())
                // Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder().setMsgContent(msg_content)
                        .setTitle(msg_title)
                        .addExtra("message extras key", extrasparam).build())

                .setOptions(Options.newBuilder()
                        // 此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(false)
                        // 此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        // 此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400).build());
        return builder.build();
    }

    private PushPayload buildPushObject_all_registrationId_alertWithTitle(
            String registrationId, String notification_title, String msg_title,
            String msg_content, String extrasparam) {

        System.out.println("----------buildPushObject_all_all_alert");
        // 创建一个IosAlert对象，可指定APNs的alert、title等字段
        // IosAlert iosAlert = IosAlert.newBuilder().setTitleAndBody("title",
        // "alert body").build();

        return PushPayload.newBuilder()
                // 指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.all())
                // 指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration
                // id
                .setAudience(Audience.registrationId(registrationId))
                // jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        // 指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification
                                .newBuilder()

                                .setAlert(notification_title)
                                .setTitle(notification_title)
                                // 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("androidNotification extras key",
                                        extrasparam)

                                .build())
                        // 指定当前推送的iOS通知
                        .addPlatformNotification(IosNotification.newBuilder()
                                // 传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(notification_title)
                                // 直接传alert
                                // 此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                // 此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("sound.caf")
                                // 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("iosNotification extras key",
                                        extrasparam)
                                // 此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                // 取消此注释，消息推送时ios将无法在锁屏情况接收
                                // .setContentAvailable(true)

                                .build())

                        .build())
                // Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()

                        .setMsgContent(msg_content)

                        .setTitle(msg_title)

                        .addExtra("message extras key", extrasparam)

                        .build())

                .setOptions(Options.newBuilder()
                        // 此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(false)
                        // 此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        // 此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
                        .setTimeToLive(86400)

                        .build())

                .build();

    }

    private static PushPayload buildPushObject_android_all_alertWithTitle(
            String notification_title, String notification_content,
            String msg_title, String msg_content, String extrasparam,
            String userid) {
        System.out.println(
                "----------buildPushObject_android_registrationId_alertWithTitle");
        return PushPayload.newBuilder()
                // 指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.android())
                // 指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration
                // id
                .setAudience(Audience.alias(userid))
                // jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        // 指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification
                                .newBuilder().setAlert(notification_content)// 通知内容
                                .setTitle(notification_title)// 通知标题
                                // 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("type", extrasparam).build())
                        .build())
                // Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                /*
                 * .setMessage(Message.newBuilder() .setMsgContent(msg_content)
                 * .setTitle(msg_title)
                 * .addExtra("message extras key",extrasparam) .build())
                 */
                .setOptions(Options.newBuilder()
                        // 此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(false)
                        // 此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        // 此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400).build())
                .build();
    }

    private PushPayload buildPushObject_ios_all_alertWithTitle(
            String notification_title, String msg_title, String msg_content,
            String extrasparam) {
        System.out.println(
                "----------buildPushObject_ios_registrationId_alertWithTitle");
        return PushPayload.newBuilder()
                // 指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.ios())
                // 指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration
                // id
                .setAudience(Audience.all())
                // jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        // 指定当前推送的android通知
                        .addPlatformNotification(IosNotification.newBuilder()
                                // 传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(notification_title)
                                // 直接传alert
                                // 此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                // 此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("sound.caf")
                                // 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("iosNotification extras key",
                                        extrasparam)
                                // 此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                // .setContentAvailable(true)

                                .build())
                        .build())
                // Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder().setMsgContent(msg_content)
                        .setTitle(msg_title)
                        .addExtra("message extras key", extrasparam).build())

                .setOptions(Options.newBuilder()
                        // 此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(false)
                        // 此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        // 此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400).build())
                .build();
    }

    /**
     * 线程对列
     */
    private static BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(
            10); // 固定为10的线程队列
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 10,
            1, TimeUnit.HOURS, queue);

    /**
     * 封装 executor
     * 
     * @param sysPushmessage
     * @param jsonObject
     * @return
     */
    public static void getExecutor(String jpushAppKey, String jpushSecret,
            String title, String content, String jsonString, String alias) {
        executor.execute(getThread(jpushAppKey, jpushSecret, title, content,
                jsonString, alias));

    }

    private static Runnable getThread(String jpushAppKey, String jpushSecret,
            String title, String content, String jsonString, String alias) {
        return new Runnable() {
            public void run() {
                try {
                    getInstance(jpushAppKey, jpushSecret).sendToAllAlias(title,
                            title, content, jsonString, alias);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static void main(String[] args) {
        if (JpushClientUtil.getInstance("bc373e9865c1f16bd7fd7961",
                "2baa0508d0b81c60bbac2fdb").sendToAllAlias("提醒标题", "信息标题",
                        "信息内容", new HashMap<String, Object>(),
                        "5d2537c0f360141e3cc0bae3") == 1) {
            System.out.println("success");
        }
    }
}
