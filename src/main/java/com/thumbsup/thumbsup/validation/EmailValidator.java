package com.thumbsup.thumbsup.validation;

import com.thumbsup.thumbsup.service.interfaces.ICustomerService;
import com.thumbsup.thumbsup.service.interfaces.IStoreService;
import com.thumbsup.thumbsup.validation.interfaces.EmailConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<EmailConstraint, Object> {

    private final ICustomerService customerService;

    private final IStoreService storeService;

    @Override
    public void initialize(EmailConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            constraintValidatorContext.disableDefaultConstraintViolation();
            if (value.toString().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                String email = value.toString();
                if (customerService.checkByEmail(email) && storeService.checkByEmail(email)) {
                    return true;
                }
                constraintValidatorContext.buildConstraintViolationWithTemplate("Email is already in use").addConstraintViolation();
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
