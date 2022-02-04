package com.enigma.api.inventory.models.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AlphabeticValidator.class)
@Documented
public @interface Alphabetic {

    String message() default "{Alphabetic}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
