package br.com.hzup.desafioproposta.compartilhado.erro;

import org.springframework.http.HttpStatus;

//classe de erro para generalizar as excecoes do sistema
public class ApiErroException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private final HttpStatus httpStatus;
	private final String reason;

	public ApiErroException(HttpStatus httpStatus, String reason) {
	        this.httpStatus = httpStatus;
	        this.reason = reason;
	    }

	public HttpStatus getHttpStatus() {return httpStatus;}
	public String getReason() {return reason;}
}
