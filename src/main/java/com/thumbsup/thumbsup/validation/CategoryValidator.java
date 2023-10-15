package com.thumbsup.thumbsup.validation;

import com.thumbsup.thumbsup.service.interfaces.ICategoryService;
import com.thumbsup.thumbsup.validation.interfaces.CategoryConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryValidator implements ConstraintValidator<CategoryConstraint, Object> {

    private final ICategoryService categoryService;

    @Override
    public void initialize(CategoryConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return categoryService.getCategoryById(Long.parseLong(value.toString())) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
