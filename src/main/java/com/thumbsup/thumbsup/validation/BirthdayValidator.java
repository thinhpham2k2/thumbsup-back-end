package com.thumbsup.thumbsup.validation;

import com.thumbsup.thumbsup.validation.interfaces.BirthdayConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class BirthdayValidator implements ConstraintValidator<BirthdayConstraint, Object> {

    @Override
    public void initialize(BirthdayConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            constraintValidatorContext.disableDefaultConstraintViolation();
            LocalDate localDate = LocalDate.parse(value.toString());
            LocalDate now = LocalDate.now();
            if (localDate.isBefore(now.minusYears(100)) || localDate.isAfter(now.minusYears(13))) {
                constraintValidatorContext.buildConstraintViolationWithTemplate("Valid age must be greater than 13 and less than 100").addConstraintViolation();
                return false;
            }
            return true;
        } catch (Exception e) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Invalid birthday").addConstraintViolation();
            return false;
        }
    }
}
