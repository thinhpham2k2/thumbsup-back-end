package com.thumbsup.thumbsup.validation;

import com.thumbsup.thumbsup.service.interfaces.IStoreService;
import com.thumbsup.thumbsup.validation.interfaces.StoreConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StoreValidator implements ConstraintValidator<StoreConstraint, Object> {

    private final IStoreService storeService;

    @Override
    public void initialize(StoreConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return storeService.getStoreById(true, Long.parseLong(value.toString())) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
