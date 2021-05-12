package br.com.hzup.desafioproposta.cartao.bloqueio;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.hzup.desafioproposta.cartao.Cartao;

@Entity
public class Bloqueio {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private LocalDateTime dataBloqueio = LocalDateTime.now();
	@NotBlank
	private String ipBloqueio;
	@NotBlank
	private String userAgent;
	@OneToOne @NotNull 
	private Cartao cartaoBloqueado;
	
	//utilizado pelo hibernate apenas
	@Deprecated
	public Bloqueio() {}

	public Bloqueio(BloqueioRequest bloqueioReq, Cartao cartao) {
		this.ipBloqueio = bloqueioReq.getIpBloqueio();
		this.userAgent = bloqueioReq.getUserAgent();
		this.cartaoBloqueado = cartao;
	}
}
