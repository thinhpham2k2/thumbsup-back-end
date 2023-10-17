package com.thumbsup.thumbsup.validation;

import com.thumbsup.thumbsup.validation.interfaces.RatingConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RatingValidator implements ConstraintValidator<RatingConstraint, Object> {
    @Override
    public void initialize(RatingConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            constraintValidatorContext.disableDefaultConstraintViolation();
            int rating = Integer.parseInt(value.toString());
            if (0 > rating || rating > 5) {
                constraintValidatorContext.buildConstraintViolationWithTemplate("Rating must be between 0 and 5").addConstraintViolation();
                return false;
            }
            return true;
        } catch (Exception e) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Rating must be an integer").addConstraintViolation();
            return false;
        }
    }
}
