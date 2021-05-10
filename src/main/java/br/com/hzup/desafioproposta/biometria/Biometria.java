package br.com.hzup.desafioproposta.biometria;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import br.com.hzup.desafioproposta.cartao.Cartao;

@Entity
public class Biometria {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull @Lob
	private byte[] biometria;
	
	@ManyToOne
	private Cartao cartao;
	
	@PastOrPresent
	private LocalDateTime dataCriacao = LocalDateTime.now();

	public Biometria(@NotBlank byte[] biometria, Cartao cartao) {
		this.biometria = biometria;
		this.cartao = cartao;
	}

	public Long getId() {
		return id;
	}
}
