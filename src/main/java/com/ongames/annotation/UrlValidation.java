package com.ongames.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;


@Documented
@Constraint (validatedBy = UrlValidator.class)
@Target ({ElementType.FIELD, ElementType.METHOD})
@Retention (RetentionPolicy.RUNTIME)
public @interface UrlValidation {
    String message() default "URL inválida";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
}
