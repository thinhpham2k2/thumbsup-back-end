package com.thumbsup.thumbsup.validation;

import com.thumbsup.thumbsup.service.interfaces.ICustomerService;
import com.thumbsup.thumbsup.service.interfaces.IStoreService;
import com.thumbsup.thumbsup.validation.interfaces.UsernameConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UsernameValidator implements ConstraintValidator<UsernameConstraint, Object> {

    private final IStoreService storeService;

    private final ICustomerService customerService;

    @Override
    public void initialize(UsernameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            constraintValidatorContext.disableDefaultConstraintViolation();
            if (value.toString().matches("[a-z0-9]{3,30}")) {
                String userName = value.toString();
                System.out.println("Hayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
                if (customerService.checkByUsername(userName) && storeService.checkByUsername(userName)) {
                    return true;
                }
                constraintValidatorContext.buildConstraintViolationWithTemplate("Username is already in use").addConstraintViolation();
            } else {
                constraintValidatorContext.buildConstraintViolationWithTemplate("Username must contain lowercase " +
                        "letters or numbers, and be between 3 and 30 characters in length").addConstraintViolation();
            }
            return false;
        } catch (Exception e) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Invalid username").addConstraintViolation();
            return false;
        }
    }
}
