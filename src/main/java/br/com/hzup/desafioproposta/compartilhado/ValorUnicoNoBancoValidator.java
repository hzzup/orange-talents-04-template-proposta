package br.com.hzup.desafioproposta.compartilhado;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.http.HttpStatus;

public class ValorUnicoNoBancoValidator implements ConstraintValidator<ValorUnicoNoBanco, Object>{

	//Campo que é recebido no parametro $campo na anotação, é o valor na tabela a ser pesquisada
    private String campoASerPesquisado;
    //Recebemos com uma classe genérica, pois não sabemos qual tabela iremos procurar
    //No caso é a tabela no banco de dados, no caso nossa entity
    private Class<?> tabela;
    
    @PersistenceContext private EntityManager entityManager;
    
    @Override
    public void initialize(ValorUnicoNoBanco constraintAnnotation) {
        this.tabela = constraintAnnotation.tabela();
        this.campoASerPesquisado = constraintAnnotation.campo();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

    	//Como recebemos tudo como genérico conseguimos criar uma query padrão para nos retornar
    	//apenas um valor caso já seja encontrado.
        Boolean valorJaExiste = entityManager.createQuery("select count(t) < 1 from "
                + tabela.getName()
                + " t  where " + campoASerPesquisado + " = :valor", Boolean.class)
                .setParameter("valor", value)
                .getSingleResult();
        
        if (!valorJaExiste) throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY,"Valor de " + campoASerPesquisado + "já consta na base");
        return valorJaExiste;
    }
}
