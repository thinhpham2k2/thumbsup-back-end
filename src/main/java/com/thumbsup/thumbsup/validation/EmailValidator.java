package com.thumbsup.thumbsup.validation;

import com.thumbsup.thumbsup.service.interfaces.ICustomerService;
import com.thumbsup.thumbsup.service.interfaces.IStoreService;
import com.thumbsup.thumbsup.validation.interfaces.EmailConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<EmailConstraint, Object> {

    private final IStoreService storeService;

    private final ICustomerService customerService;

    @Override
    public void initialize(EmailConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            constraintValidatorContext.disableDefaultConstraintViolation();
            if (value.toString().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                return true;
            } else {
                constraintValidatorContext.buildConstraintViolationWithTemplate("Invalid email formatter").addConstraintViolation();
            }
            return false;
        } catch (Exception e) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Invalid email").addConstraintViolation();
            return false;
        }
    }
}
