package com.w4t3rcs.task.validation.annotation;

import com.w4t3rcs.task.validation.AllowedAgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = AllowedAgeValidator.class)
public @interface AllowedAge {
    String message() default "Age isn't allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
