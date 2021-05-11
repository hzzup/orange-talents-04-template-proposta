package br.com.hzup.desafioproposta.cartao.bloqueio;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BloqueioRequest {
	@NotBlank
	private String ipBloqueio;
	private String userAgent;

	public BloqueioRequest(@NotBlank String ipBloqueio, @NotBlank String userAgent) {
		this.ipBloqueio = ipBloqueio;
		this.userAgent = userAgent;
	}

	public String getIpBloqueio() {
		return ipBloqueio;
	}

	public String getUserAgent() {
		return userAgent;
	}
}
