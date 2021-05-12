package br.com.hzup.desafioproposta.cartao.bloqueio;

import javax.validation.constraints.NotBlank;

//valores que devemos pegar na requisicao do cliente para criar um novo bloqueio 
public class BloqueioRequest {
	@NotBlank
	private String ipBloqueio;
	@NotBlank
	private String userAgent;

	public BloqueioRequest(@NotBlank String ipBloqueio, @NotBlank String userAgent) {
		this.ipBloqueio = ipBloqueio;
		this.userAgent = userAgent;
	}

	public String getIpBloqueio() {return ipBloqueio;}
	public String getUserAgent() {return userAgent;}
}
