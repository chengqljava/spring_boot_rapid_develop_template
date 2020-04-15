package com.hwsafe.ottService.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
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
import com.hwsafe.ottService.base.exception.RestException;
import com.hwsafe.ottService.base.utils.AppResponse;
import com.hwsafe.ottService.base.utils.Context;
import com.hwsafe.ottService.base.utils.SignUtil;

/**
 * 签名验证拦截器<br>
 * initParam:
 * <li>timestamp_out_seconds:时间戳过期秒数，小于等于0表示不过期</li>
 * <li>key_secret_map:appKey,appSecret映射字符串格式如：key1=secret1,key2=secret2</li>
 * 
 * @version $Id: ProtocolFilter.java, v 0.1 2015年3月24日 下午4:40:30 chengql Exp $
 */
public class ProtocolFilter implements Filter {
    private static final Logger logger = LoggerFactory
            .getLogger(ProtocolFilter.class);
    private List<String> ignoreURIs = new ArrayList<String>();

    @Override
    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        /*
         * CommonsMultipartResolver multipartResolver = SpringContextHolder
         * .getBean("multipartResolver"); if (multipartResolver != null &&
         * multipartResolver.isMultipart(request)) { if (request instanceof
         * MultipartHttpServletRequest) { //do nothing } else { request =
         * multipartResolver.resolveMultipart(request); } }
         */
        String req_uri = request.getRequestURI();
        boolean flag = true;
        // 过滤URL前缀
        for (String ignoreURI : ignoreURIs) {
            if (req_uri.contains(ignoreURI)) {
                flag = false;
                break;
            }
        }
        try {
            if (flag) {
                SignUtil.validateSign(request,
                        Context.getWebStartIngredients().getAppSecret());
            }
            filterChain.doFilter(request, servletResponse);
        } catch (RestException e) {
            String strBackUrl = "http://" + request.getServerName() // 服务器地址
                    + ":" + request.getServerPort() // 端口号
                    + request.getContextPath() // 项目名称
                    + request.getServletPath() // 请求页面或其他地址
                    + "?" + (request.getQueryString());
            StringBuilder parameterSb = new StringBuilder();
            Enumeration<String> names = request.getParameterNames();
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                if (StringUtils.isNotBlank(name)
                        && StringUtils.isNotBlank(request.getParameter(name))) {
                    parameterSb.append("&").append(name).append("=")
                            .append(request.getParameter(name));
                }
            }
            logger.error(e.getMessage() + ",url:" + strBackUrl + ",params:"
                    + parameterSb.toString());

            response.setStatus(HttpStatus.OK.value());
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(
                    JSON.toJSONString(AppResponse.fail(e.getMessage())));
            response.flushBuffer();
            return;
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
        } catch (Exception e) {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(
                    JSON.toJSONString(AppResponse.server_inner_error()));
            response.flushBuffer();
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // ignoreURIs.add("/login");
        // ignoreURIs.add("/swagger-ui.html");
        // ignoreURIs.add("/webjars");
        // ignoreURIs.add("/swagger-resources");
        // ignoreURIs.add("/favicon.ico");
        // ignoreURIs.add("/v2/api-docs");
//        ignoreURIs.add("/mobile/");
        /*
         * String key_secret_map =
         * filterConfig.getInitParameter("key_secret_map"); if
         * (StringUtils.isNotBlank(key_secret_map)) { String[] mapArr =
         * key_secret_map.split(","); if (ArrayUtils.isNotEmpty(mapArr)) { for
         * (int i = 0; i < mapArr.length; i++) { if
         * (StringUtils.isNoneBlank(mapArr[i])) { String[] keySecretArr =
         * mapArr[i].split("="); if (keySecretArr.length != 2) throw new
         * RuntimeException(
         * "error init param[key_secret_map] for ProtocolFilter");
         * Config.SECRET_KEY_MAP.put(keySecretArr[0], keySecretArr[1]); } } } }
         * String ignores = filterConfig.getInitParameter("ignore"); if (ignores
         * != null) for (String ig : StringUtils.split(ignores, ','))
         * ignoreURIs.add(ig.trim());
         */
    }

    @Override
    public void destroy() {
    }
}
