package br.com.hzup.desafioproposta.compartilhado.validacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ValorUnicoNoBancoValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValorUnicoNoBanco {

    String message() default "O valor já consta no banco de dados";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    Class<?> tabela();

    String campo();

}