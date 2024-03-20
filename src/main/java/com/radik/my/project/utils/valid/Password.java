package com.radik.my.project.utils.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    String message() default "Пароль должен содержать цифры, символы нижнего и верхнего регистра";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
