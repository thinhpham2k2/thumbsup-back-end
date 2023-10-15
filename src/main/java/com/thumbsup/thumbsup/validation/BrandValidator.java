package com.thumbsup.thumbsup.validation;

import com.thumbsup.thumbsup.service.interfaces.IBrandService;
import com.thumbsup.thumbsup.validation.interfaces.BrandConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BrandValidator implements ConstraintValidator<BrandConstraint, Object> {

    private final IBrandService brandService;

    @Override
    public void initialize(BrandConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return brandService.getBrandById(Long.parseLong(value.toString())) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
