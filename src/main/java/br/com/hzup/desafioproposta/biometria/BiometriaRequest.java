package br.com.hzup.desafioproposta.biometria;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import br.com.hzup.desafioproposta.cartao.Cartao;

//classe com os atributos enviados pelo cliente na requisicao
public class BiometriaRequest {

	@NotNull @Lob
	private byte[] biometria;

	public byte[] getBiometria() {
		return biometria;
	}
	
	//transformar minha classe request na classe entidade
	public Biometria toModel(Cartao cartao) {
		return new Biometria(biometria,cartao);
	}
}
