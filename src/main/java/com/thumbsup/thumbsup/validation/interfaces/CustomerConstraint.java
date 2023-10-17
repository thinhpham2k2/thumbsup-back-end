package com.thumbsup.thumbsup.validation.interfaces;

import com.thumbsup.thumbsup.validation.CustomerValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CustomerValidator.class)
public @interface CustomerConstraint {
    String message() default "Invalid customer";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
