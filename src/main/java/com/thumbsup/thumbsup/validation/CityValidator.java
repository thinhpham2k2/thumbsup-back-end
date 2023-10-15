package com.thumbsup.thumbsup.validation;

import com.thumbsup.thumbsup.service.interfaces.ICityService;
import com.thumbsup.thumbsup.validation.interfaces.CityConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CityValidator implements ConstraintValidator<CityConstraint, Object> {

    private final ICityService cityService;

    @Override
    public void initialize(CityConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return cityService.getCityById(Long.parseLong(value.toString())) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
