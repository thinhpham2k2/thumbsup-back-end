package com.thumbsup.thumbsup.validation;

import com.thumbsup.thumbsup.service.interfaces.IOrderService;
import com.thumbsup.thumbsup.validation.interfaces.OrderConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderValidator implements ConstraintValidator<OrderConstraint, Object> {

    private final IOrderService orderService;

    @Override
    public void initialize(OrderConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return orderService.getOrderById(Long.parseLong(value.toString())) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
