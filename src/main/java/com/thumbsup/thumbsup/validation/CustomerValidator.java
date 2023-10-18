package com.thumbsup.thumbsup.validation;

import com.thumbsup.thumbsup.service.interfaces.ICustomerService;
import com.thumbsup.thumbsup.validation.interfaces.CustomerConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerValidator implements ConstraintValidator<CustomerConstraint, Object> {

    private final ICustomerService customerService;

    @Override
    public void initialize(CustomerConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return customerService.getCustomerById(Long.parseLong(value.toString()), true) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
