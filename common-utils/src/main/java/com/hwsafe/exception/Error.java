package com.hwsafe.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.metadata.ConstraintDescriptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;
import com.hwsafe.validate.CheckException;

public class Error implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(Error.class);

    /**  */
    private static final long serialVersionUID = -8760400175621795051L;

    private String code;
    private String messageTemplate;
    private Object[] messageArgs;
    private String trace;
    private List<SubError> errors = new ArrayList<Error.SubError>();

    // for serializable
    private Error() {
    }

    public static Error build(String code, String messageTemplate,
            Object[] messageArgs) {
        Error result = new Error();
        result.setCode(code);
        result.setMessageTemplate(messageTemplate);
        result.setMessageArgs(messageArgs);
        return result;
    }

    public static Error from(Throwable e) {
        Error result = new Error();
        if (e instanceof CheckException) {
            result = ((CheckException) e).getError();
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) e;
            result = from(ex);
        } else if (e instanceof BizException) {
            result = ((BizException) e).getError();
        } else {
            result.code = "500";
            result.messageTemplate = "error.internalServer";
        }
        if (logger.isDebugEnabled()) {
            result.trace = Throwables.getStackTraceAsString(e);
        }
        return result;
    }

    private static Error from(ConstraintViolationException e) {
        Error result = new Error();
        result.code = "param_error";
        result.messageTemplate = "error.illegalArgument";

        String subErrorKey = null;
        String subErrorMessageTemplate = null;
        Object[] subErrorMessageArgs = null;
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            subErrorKey = violation.getPropertyPath().toString();

            ConstraintDescriptor descriptor = violation
                    .getConstraintDescriptor();
            String annotationTypeName = descriptor.getAnnotation()
                    .annotationType().getName();
            if ("javax.validation.constraints.AssertFalse"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.AssertFalse";
            } else if ("javax.validation.constraints.AssertTrue"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.AssertTrue";
            } else if ("javax.validation.constraints.DecimalMax"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.DecimalMax";
                subErrorMessageArgs = new Object[] {
                        descriptor.getAttributes().get("value") };
            } else if ("javax.validation.constraints.DecimalMin"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.DecimalMin";
                subErrorMessageArgs = new Object[] {
                        descriptor.getAttributes().get("value") };
            } else if ("javax.validation.constraints.Digits"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.Digits";
                subErrorMessageArgs = new Object[] {
                        descriptor.getAttributes().get("integer"),
                        descriptor.getAttributes().get("fraction") };
            } else if ("javax.validation.constraints.Future"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.Future";
            } else if ("javax.validation.constraints.Max"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.Max";
                subErrorMessageArgs = new Object[] {
                        descriptor.getAttributes().get("value") };
            } else if ("javax.validation.constraints.Min"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.Min";
                subErrorMessageArgs = new Object[] {
                        descriptor.getAttributes().get("value") };
            } else if ("javax.validation.constraints.NotNull"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.NotNull";
            } else if ("javax.validation.constraints.Null"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.Null";
            } else if ("javax.validation.constraints.Past"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.Past";
            } else if ("javax.validation.constraints.Pattern"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.Pattern";
                subErrorMessageArgs = new Object[] {
                        descriptor.getAttributes().get("regexp") };
            } else if ("javax.validation.constraints.Size"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.Size";
                subErrorMessageArgs = new Object[] {
                        descriptor.getAttributes().get("min"),
                        descriptor.getAttributes().get("max") };
            } else if ("org.hibernate.validator.constraints.CreditCardNumber"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.CreditCardNumber";
            } else if ("org.hibernate.validator.constraints.Email"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.Email";
            } else if ("org.hibernate.validator.constraints.Length"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.Length";
                subErrorMessageArgs = new Object[] {
                        descriptor.getAttributes().get("min"),
                        descriptor.getAttributes().get("max") };
            } else if ("org.hibernate.validator.constraints.NotBlank"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.NotBlank";
            } else if ("org.hibernate.validator.constraints.NotEmpty"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.NotEmpty";
            } else if ("org.hibernate.validator.constraints.Range"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.Range";
                subErrorMessageArgs = new Object[] {
                        descriptor.getAttributes().get("min"),
                        descriptor.getAttributes().get("max") };
            } else if ("org.hibernate.validator.constraints.SafeHtml"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.SafeHtml";
            } else if ("org.hibernate.validator.constraints.ScriptAssert"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.ScriptAssert";
                subErrorMessageArgs = new Object[] {
                        descriptor.getAttributes().get("script") };
            } else if ("org.hibernate.validator.constraints.URL"
                    .equalsIgnoreCase(annotationTypeName)) {
                subErrorMessageTemplate = "error.illegalArgument.URL";
            } else {
                subErrorMessageTemplate = "error.illegalArgument";
            }
            result.getErrors().add(new SubError(subErrorKey,
                    subErrorMessageTemplate, subErrorMessageArgs));
        }

        return result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public void setMessageTemplate(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public Object[] getMessageArgs() {
        return messageArgs;
    }

    public void setMessageArgs(Object[] messageArgs) {
        this.messageArgs = messageArgs;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public List<SubError> getErrors() {
        return errors;
    }

    public void setErrors(List<SubError> errors) {
        this.errors = errors;
    }

    public static final class SubError implements Serializable {

        /**  */
        private static final long serialVersionUID = 3078656311949232210L;

        private String key;
        private String messageTemplate;
        private Object[] messageArgs;

        public SubError() {
        }

        public SubError(String key, String messageTemplate,
                Object... messageArgs) {
            this.key = key;
            this.messageTemplate = messageTemplate;
            this.messageArgs = messageArgs;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getMessageTemplate() {
            return messageTemplate;
        }

        public void setMessageTemplate(String messageTemplate) {
            this.messageTemplate = messageTemplate;
        }

        public Object[] getMessageArgs() {
            return messageArgs;
        }

        public void setMessageArgs(Object[] messageArgs) {
            this.messageArgs = messageArgs;
        }

    }
}
