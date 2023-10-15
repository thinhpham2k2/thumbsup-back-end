package com.thumbsup.thumbsup.validation;

import com.thumbsup.thumbsup.service.interfaces.ICountryService;
import com.thumbsup.thumbsup.validation.interfaces.CountryConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CountryValidator implements ConstraintValidator<CountryConstraint, Object> {

    private final ICountryService countryService;

    @Override
    public void initialize(CountryConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return countryService.getCountryById(Long.parseLong(value.toString())) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
