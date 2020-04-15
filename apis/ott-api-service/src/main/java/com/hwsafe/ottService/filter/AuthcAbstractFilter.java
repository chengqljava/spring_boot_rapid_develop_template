package com.hwsafe.ottService.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSON;
import com.hwsafe.ottService.base.exception.AppKeyException;
import com.hwsafe.ottService.base.exception.IpException;
import com.hwsafe.ottService.base.exception.ParamException;
import com.hwsafe.ottService.base.utils.AppResponse;
import com.hwsafe.ottService.base.utils.Context;
import com.hwsafe.ottService.base.utils.ProtocolParams;
import com.hwsafe.utils.IPUtil;

/**
 * 
 * @author
 * @version
 */
public abstract class AuthcAbstractFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private List<String> ignoreURIs = new ArrayList<String>();

    @Override
    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {

            String req_uri = request.getRequestURI();
            // 过滤URL前缀
            for (String ignoreURI : ignoreURIs) {
                if (req_uri.startsWith(ignoreURI)) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }
            // 配置信息AppKeyException
            isWebStartIngredientsConfig(
                    request.getParameter(ProtocolParams.APP_KEY));
            // 白名单
            if (StringUtils.isNoneBlank(
                    Context.getWebStartIngredients().getIpWhite())) {
                String ip = IPUtil.getIpAddress(request);
                if (!Context.getWebStartIngredients().getIpWhite()
                        .contains(ip)) {
                    // throw new IpException("ip不在白名单");
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
            afterDoFilter();
        } catch (IpException e) {
            logger.warn(e.getMessage());

            response.setStatus(HttpStatus.OK.value());
            response.setContentType("text/html;charset=utf-8");
            response.getWriter()
                    .println(JSON.toJSONString(AppResponse.ip_error()));
            response.flushBuffer();
            return;
        } catch (AppKeyException e) {
            logger.warn(e.getMessage());

            response.setStatus(HttpStatus.OK.value());
            response.setContentType("text/html;charset=utf-8");
            response.getWriter()
                    .println(JSON.toJSONString(AppResponse.app_key_error()));
            response.flushBuffer();
            return;
        } catch (ParamException e) {
            logger.warn(e.getMessage());
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("text/html;charset=utf-8");
            response.getWriter()
                    .println(JSON.toJSONString(AppResponse.app_param_error()));
            response.flushBuffer();
            return;
        } catch (Exception e) {
            logger.error("authcFilter发生未知异常", e);

            response.setStatus(HttpStatus.OK.value());
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(
                    JSON.toJSONString(AppResponse.server_inner_error()));
            response.flushBuffer();
            return;
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // ignoreURIs.add("/test");
        // ignoreURIs.add("/login");
        // ignoreURIs.add("/swagger-ui.html");
        // ignoreURIs.add("/webjars");
        // ignoreURIs.add("/swagger-resources");
        // ignoreURIs.add("/v2/api-docs");
        // ignoreURIs.add("/mobile/system/login");
        // ignoreURIs.add("/mobile/hidden/attach");
        // ignoreURIs.add("//mobile/hidden/attach");
//        ignoreURIs.add("/mobile/");
        /*
         * String token_expired_switch =
         * filterConfig.getInitParameter("token_expired_switch"); if
         * (StringUtils.equals("true", token_expired_switch) ||
         * StringUtils.equals("false", token_expired_switch)) {
         * Config.setTOKEN_EXPIRED_SWITCH(Boolean.valueOf(token_expired_switch))
         * ; }
         *
         * String token_expired_minutes =
         * filterConfig.getInitParameter("token_expired_minutes"); if
         * (NumberUtils.isDigits(token_expired_minutes)) {
         * Config.setTOKEN_EXPIRED_MINUTES(Integer.valueOf(token_expired_minutes
         * )); }
         *
         * //某些URL前缀不予处理（例如 /img/***） String ignores =
         * filterConfig.getInitParameter("ignore"); if (ignores != null) for
         * (String ig : StringUtils.split(ignores, ','))
         * ignoreURIs.add(ig.trim());
         */

    }

    @Override
    public void destroy() {
    }

    public void afterDoFilter() {
        Context.removeTenant();
        Context.remvoeWebStartIngredients();
    }

    /**
     * 判断appkey是否有效
     *
     * @param appKey
     * @return
     */
    public abstract String isWebStartIngredientsConfig(String appKey);

}
