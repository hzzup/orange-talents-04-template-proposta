package br.com.hzup.desafioproposta.compartilhado;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

@ConstraintComposition(CompositionType.OR)
@CPF 
@CNPJ
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface CpfOuCnpj {

    String message() default "O campo deve ser um cnpj ou cpf valido";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}