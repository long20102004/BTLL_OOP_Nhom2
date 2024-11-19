package com.example.demo.custom_annotation;

import com.example.demo.validator.PasswordMatchesValidator;
import com.example.demo.validator.UserExistedValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserExistedValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UserExisted {
    String message() default "This email has been used, using other email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}