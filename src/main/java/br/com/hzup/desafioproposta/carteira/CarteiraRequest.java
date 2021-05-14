package br.com.hzup.desafioproposta.carteira;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.hzup.desafioproposta.cartao.Cartao;
import br.com.hzup.desafioproposta.carteira.externo.CarteiraLegadoClient.CarteiraResponse;

public class CarteiraRequest {

	@NotBlank @Email 
	private String email;
	@Enumerated(EnumType.STRING) @NotNull
	private tipo_carteira carteira; 
	
	public String getEmail() {return email;}
	public tipo_carteira getCarteira() {return carteira;}

	//enum com os tipos de carteiras disponiveis
	public enum tipo_carteira {
		PAYPAL
	}
	
	public CarteiraRequest(@NotBlank @Email String email, @NotBlank tipo_carteira carteira) {
		this.email = email;
		this.carteira = carteira;
	}
	public Carteira toModel(CarteiraResponse respostaLegado, Cartao cartao) {
		return new Carteira(this.email, this.carteira, cartao, respostaLegado.getId(), respostaLegado.getResultado());
	}
}
