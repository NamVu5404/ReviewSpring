package com.NamVu.ReviewSpring.util;

import com.NamVu.ReviewSpring.enums.Gender;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Constraint(validatedBy = GenderSubsetValidator.class)
public @interface GenderSubset {
    Gender[] anyOf();

    String message() default "must be any of {anyOf}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
