package com.thumbsup.thumbsup.validation;

import com.thumbsup.thumbsup.service.interfaces.IProductService;
import com.thumbsup.thumbsup.validation.interfaces.ProductConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductValidator implements ConstraintValidator<ProductConstraint, Object> {

    private final IProductService productService;

    @Override
    public void initialize(ProductConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return productService.getProductById(true, Long.parseLong(value.toString())) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
