package com.hwsafe.weixin.filter;

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
import com.hwsafe.weixin.base.exception.RestException;
import com.hwsafe.weixin.base.exception.RestTokenException;
import com.hwsafe.weixin.base.exception.RestTokenExpireException;
import com.hwsafe.weixin.base.utils.AppResponse;
import com.hwsafe.weixin.base.utils.Context;
import com.hwsafe.weixin.base.utils.JwtHelper;

import io.jsonwebtoken.Claims;

/**
 * 判断是否登录拦截器 <br>
 * initParam:
 * <li>token_expired_switch:token是否需要验证过期,true/false</li>
 * <li>token_expired_minutes:token过期时间，分钟</li>
 * <li>exclude_url:排除的url</li>
 *
 * @author
 * @version $Id: AuthcFilter.java, v 0.1 2015年3月24日 下午5:30:54 eric Exp $
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

            String tokenString = request.getHeader("authorization");
            if (StringUtils.isBlank(tokenString))
                throw new RestTokenException();
            Claims claims = JwtHelper.parseJWT(tokenString,
                    JwtHelper.BASE64SECURITY);
            /*
             * SysUser sysUser =
             * isSysUserValid(claims.get("userid").toString()); if (sysUser ==
             * null) { throw new RestTokenExpireException("用户不存在"); }
             * Context.setSysUser(sysUser);
             */
            filterChain.doFilter(servletRequest, servletResponse);
            afterDoFilter();
        } catch (RestException e) {
            logger.warn(e.getMessage());

            response.setStatus(HttpStatus.OK.value());
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(
                    JSON.toJSONString(AppResponse.fail(e.getMessage())));
            response.flushBuffer();
        } catch (RestTokenException e) {
            logger.warn(e.getMessage());

            response.setStatus(HttpStatus.OK.value());
            response.setContentType("text/html;charset=utf-8");
            response.getWriter()
                    .println(JSON.toJSONString(AppResponse.token_error()));
            response.flushBuffer();
        } catch (RestTokenExpireException e) {
            logger.warn(e.getMessage());

            response.setStatus(HttpStatus.OK.value());
            response.setContentType("text/html;charset=utf-8");
            response.getWriter()
                    .println(JSON.toJSONString(AppResponse.token_expired()));
            response.flushBuffer();
        } catch (Exception e) {
            logger.error("authcFilter发生未知异常", e);

            response.setStatus(HttpStatus.OK.value());
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(
                    JSON.toJSONString(AppResponse.server_inner_error()));
            response.flushBuffer();
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ignoreURIs.add("/test");
        ignoreURIs.add("/login");
        ignoreURIs.add("/swagger-ui.html");
        ignoreURIs.add("/webjars");
        ignoreURIs.add("/swagger-resources");
        ignoreURIs.add("/v2/api-docs");
        ignoreURIs.add("/mobile/system/login");
        ignoreURIs.add("/mobile/hidden/attach");
        ignoreURIs.add("//mobile/hidden/attach");
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
        Context.remove();
    }

    /**
     * 判断token是否有效，这里主要根据uid去数据库查询看是否存在相关有效记录
     *
     * @param token
     * @return
     */
    public abstract String isSysUserValid(String userid);

}
