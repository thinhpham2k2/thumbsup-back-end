package com.thumbsup.thumbsup.validation;

import com.thumbsup.thumbsup.service.interfaces.IStateService;
import com.thumbsup.thumbsup.validation.interfaces.StateConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StateValidator implements ConstraintValidator<StateConstraint, Object> {

    private final IStateService stateService;

    @Override
    public void initialize(StateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return stateService.getStateById(Long.parseLong(value.toString())) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
