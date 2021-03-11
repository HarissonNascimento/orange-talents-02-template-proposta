package br.com.zup.propostazup.annotation;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {})
@Target({FIELD})
@Retention(RUNTIME)
@ConstraintComposition(value = CompositionType.OR)
@CPF
@CNPJ
public @interface CPForCNPJ {

    String message() default "{br.com.zup.propostazup.annotation.CPForCNPJ}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
