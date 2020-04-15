package com.hwsafe.ottService.filter;

import org.apache.commons.lang3.StringUtils;

import com.hwsafe.common.SpringContextHolder;
import com.hwsafe.ottService.base.exception.AppKeyException;
import com.hwsafe.ottService.base.utils.Context;
import com.hwsafe.tenant.service.TenantService;
import com.hwsafe.webConfig.domain.WebStartIngredientsConfig;
import com.hwsafe.webConfig.service.WebStartIngredientsConfigService;

public class AuthcFilter extends AuthcAbstractFilter {

    @Override
    public String isWebStartIngredientsConfig(String appKey) {
        WebStartIngredientsConfigService webStartIngredientsConfigService = SpringContextHolder
                .getBean(WebStartIngredientsConfigService.class);
        WebStartIngredientsConfig webConfig = null;
        if (StringUtils.isNoneBlank(appKey)) {
            webConfig = webStartIngredientsConfigService.selectByAppKey(appKey);
            if (webConfig != null) {
                // 配置
                Context.setWebStartIngredients(webConfig);
                if (StringUtils.isNoneBlank(webConfig.getTenantId())) {
                    // 租户信息
                    Context.setTenant(
                            SpringContextHolder.getBean(TenantService.class)
                                    .getById(webConfig.getTenantId()));
                }
            } else {
                throw new AppKeyException("appKey错误");
            }
        } else {
            throw new AppKeyException("appKey错误");
        }
        return webConfig.getAppSecret();
    }

}