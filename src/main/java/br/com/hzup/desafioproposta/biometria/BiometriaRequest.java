package br.com.hzup.desafioproposta.biometria;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import br.com.hzup.desafioproposta.cartao.Cartao;

public class BiometriaRequest {

	@NotNull @Lob
	private byte[] biometria;

	public byte[] getBiometria() {
		return biometria;
	}
	
	public Biometria toModel(Cartao cartao) {
		Biometria biometriaNova = new Biometria(biometria,cartao);
		return biometriaNova;
	}
}
