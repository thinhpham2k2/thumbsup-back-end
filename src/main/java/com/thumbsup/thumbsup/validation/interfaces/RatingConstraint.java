package com.thumbsup.thumbsup.validation.interfaces;

import com.thumbsup.thumbsup.validation.RatingValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = RatingValidator.class)
public @interface RatingConstraint {
    String message() default "Invalid rating";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
