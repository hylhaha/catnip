package com.syeinfo.catnip.api.annotation;


import com.syeinfo.catnip.api.validator.ServiceIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ServiceIdValidator.class)
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
@Documented
public @interface ServiceId {

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String message() default "";

}
