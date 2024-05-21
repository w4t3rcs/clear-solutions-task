package com.w4t3rcs.task.validation;

import com.w4t3rcs.task.config.ApplicationContextProvider;
import com.w4t3rcs.task.exception.InvalidAgeException;
import com.w4t3rcs.task.validation.annotation.AllowedAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Data
@Component
public class AllowedAgeValidator implements ConstraintValidator<AllowedAge, LocalDate> {
    private static final String ALLOWED_AGE_PROPERTY = "ALLOWED_AGE";
    private LocalDate minimumDate;
    private String message;

    @Override
    public void initialize(AllowedAge constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        Environment environment = ApplicationContextProvider.getEnvironment();
        String ageProperty = Objects.requireNonNull(environment.getProperty(ALLOWED_AGE_PROPERTY));
        int allowedAge = Integer.parseInt(ageProperty);
        this.minimumDate = LocalDate.now().minusYears(allowedAge);
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null || !minimumDate.isAfter(localDate)) throw new InvalidAgeException(message);
        return true;
    }
}
