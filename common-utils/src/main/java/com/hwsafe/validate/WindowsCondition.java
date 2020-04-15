package com.hwsafe.validate;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author chengql 验让操作系统windoww
 *
 */
public class WindowsCondition implements Condition {
    public boolean matches(ConditionContext context,
            AnnotatedTypeMetadata metadata) {
        return context.getEnvironment().getProperty("os.name")
                .contains("Windows");
    }
}
