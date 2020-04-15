package com.hwsafe.ottService.base.utils;

import com.hwsafe.tenant.domain.Tenant;
import com.hwsafe.webConfig.domain.WebStartIngredientsConfig;

public class Context {
    private static ThreadLocal<Tenant> TENANT = new ThreadLocal<Tenant>();
    private static ThreadLocal<WebStartIngredientsConfig> WEB_START_INGREDIENTS_CONFIG = new ThreadLocal<WebStartIngredientsConfig>();

    public static void setTenant(Tenant tenant) {
        TENANT.set(tenant);
    }

    public static Tenant getTenant() {
        return TENANT.get();
    }

    public static void removeTenant() {
        TENANT.remove();
    }

    public static void remvoeWebStartIngredients() {
        WEB_START_INGREDIENTS_CONFIG.remove();
    }

    public static void setWebStartIngredients(
            WebStartIngredientsConfig webStartIngredientsConfig) {
        WEB_START_INGREDIENTS_CONFIG.set(webStartIngredientsConfig);
    }

    public static WebStartIngredientsConfig getWebStartIngredients() {
        return WEB_START_INGREDIENTS_CONFIG.get();
    }

}
