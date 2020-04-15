package com.hwsafe.webService.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.factory.annotation.Autowired;

import com.hwsafe.webService.base.config.CommonConfigMessage;

public class IpAddressInInterceptor extends AbstractPhaseInterceptor<Message> {
    @Autowired
    private CommonConfigMessage configMessage;

    public IpAddressInInterceptor() {
        super(Phase.RECEIVE);
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        HttpServletRequest request = (HttpServletRequest) message
                .get(AbstractHTTPDestination.HTTP_REQUEST);
        String ipAddress = request.getRemoteAddr(); // 取客户端IP地址

        if (StringUtils.isNoneBlank(configMessage.getIpWhiteList())
                && !configMessage.getIpWhiteList().contains(ipAddress)) {
            throw new Fault(
                    new IllegalAccessException("IP地址" + ipAddress + "未定义在白名单"));
        }
    }
}
