package br.com.hzup.desafioproposta.compartilhado;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroValidacaoHandler {

	@Autowired
	private MessageSource messageSource;	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroPadrao> handle(MethodArgumentNotValidException exception) {
		
		Collection<String> mensagens = new ArrayList<>();
		BindingResult bindingResult = exception.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		
		fieldErrors.forEach(fieldError -> {
            String message = String.format("Campo %s %s", fieldError.getField(), 
            		messageSource.getMessage(fieldError,LocaleContextHolder.getLocale()));
            mensagens.add(message);
        });
		
		ErroPadrao errors = new ErroPadrao(mensagens);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	@ExceptionHandler(ApiErroException.class)
	public ResponseEntity<ErroPadrao> handleApiErroException(ApiErroException apiErroException) {
	    Collection<String> mensagens = new ArrayList<>();
	    mensagens.add(apiErroException.getReason());

	    ErroPadrao errors = new ErroPadrao(mensagens);
	    return ResponseEntity.status(apiErroException.getHttpStatus()).body(errors);
	}
}