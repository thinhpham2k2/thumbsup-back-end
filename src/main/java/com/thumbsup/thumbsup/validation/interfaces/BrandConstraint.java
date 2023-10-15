package com.thumbsup.thumbsup.validation.interfaces;

import com.thumbsup.thumbsup.validation.BrandValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = BrandValidator.class)
public @interface BrandConstraint {
    String message() default "Invalid brand";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
