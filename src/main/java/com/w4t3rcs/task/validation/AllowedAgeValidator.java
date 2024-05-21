package com.w4t3rcs.task.validation;

import com.w4t3rcs.task.exception.InvalidAgeException;
import com.w4t3rcs.task.validation.annotation.AllowedAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AllowedAgeValidator implements ConstraintValidator<AllowedAge, LocalDate> {
    private final LocalDate minimumDate;

    @Autowired
    public AllowedAgeValidator(@Value("${ALLOWED_AGE}") Integer allowedAge) {
        this.minimumDate = LocalDate.now().minusYears(allowedAge);
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null || minimumDate.isAfter(localDate)) throw new InvalidAgeException();
        else return true;
    }
}
