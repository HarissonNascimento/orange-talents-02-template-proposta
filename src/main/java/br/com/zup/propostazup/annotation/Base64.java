package br.com.zup.propostazup.annotation;

import br.com.zup.propostazup.annotation.validator.Base64Validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {Base64Validator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface Base64 {

    String message() default "{br.com.zup.propostazup.annotation.Base64}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
