package br.com.hzup.desafioproposta.compartilhado.erro;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroValidacaoHandler {

	@ExceptionHandler(ApiErroException.class)
	public ResponseEntity<ErroPadrao> handleApiErroException(ApiErroException apiErroException) {
	    Collection<String> mensagens = new ArrayList<>();
	    mensagens.add(apiErroException.getReason());

	    ErroPadrao errors = new ErroPadrao(mensagens);
	    return ResponseEntity.status(apiErroException.getHttpStatus()).body(errors);
	}
}