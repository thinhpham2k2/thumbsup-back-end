package com.thumbsup.thumbsup.validation.interfaces;

import com.thumbsup.thumbsup.validation.DetailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DetailValidator.class)
public @interface DetailConstraint {
    String message() default "Invalid order detail";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
