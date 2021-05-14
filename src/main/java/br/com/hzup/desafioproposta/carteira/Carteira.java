package br.com.hzup.desafioproposta.carteira;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.hzup.desafioproposta.cartao.Cartao;
import br.com.hzup.desafioproposta.carteira.CarteiraRequest.tipo_carteira;
import br.com.hzup.desafioproposta.carteira.externo.CarteiraLegadoClient.associado_legado;

@Entity
public class Carteira {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotBlank @Email 
	private String email;
	@Enumerated(EnumType.STRING) @NotNull
	private tipo_carteira carteira; 
	@ManyToOne @NotNull
	private Cartao cartao;
	
	//respostas externas via feign - numero carteira e associacao com o legado
	@NotBlank
	private String carteiraNro;
	@Enumerated(EnumType.STRING) @NotNull
	private associado_legado associado;
	
	//construtor necessario apenas para o hibernate, nao utilizar!
	@Deprecated
	private Carteira() {}

	public Carteira(@NotBlank @Email String email, @NotBlank tipo_carteira carteira, Cartao cartao, String carteiraNro,
			associado_legado associado) {
		this.email = email;
		this.carteira = carteira;
		this.cartao = cartao;
		this.carteiraNro = carteiraNro;
		this.associado = associado;
	}

	public Long getId() {return id;}
}
