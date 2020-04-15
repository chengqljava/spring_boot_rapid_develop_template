package com.hwsafe.webService.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.factory.annotation.Autowired;

import com.hwsafe.exception.ErrorTipTemplate;
import com.hwsafe.webService.base.config.CommonConfigMessage;

public class CommonParamInterceptor extends AbstractPhaseInterceptor<Message> {
    @Autowired
    private CommonConfigMessage configMessage;

    public CommonParamInterceptor() {
        super(Phase.RECEIVE);
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        HttpServletRequest request = (HttpServletRequest) message
                .get(AbstractHTTPDestination.HTTP_REQUEST);
        if (!configMessage.getParameterConstant().isEmpty()) {
            for (String key : configMessage.getParameterConstant().keySet()) {
                if (!configMessage.getParameterConstant().get(key)
                        .equals(request.getAttribute(key))) {
                    throw new Fault(new IllegalAccessException(
                            key + ErrorTipTemplate.PARAMETER_NOT_NULL));
                }
            }
        }
    }

}
