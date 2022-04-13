package com.ongames.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;


@Documented
@Constraint (validatedBy = PasswordValidator.class)
@Target ({ElementType.FIELD, ElementType.METHOD})
@Retention (RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "O password precisa conter: "
            + "mínimo 6 e máximo 20 caracteres,"
            + "pelo menos uma letra maiúscula,"
            + "uma letra minúscula"
            + "e um caractere especial";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
}
