package br.com.hzup.desafioproposta.compartilhado;

public class ErroFormulario {

	private String campo;
	private String erro;

	public ErroFormulario(String campo, String erro) {
		this.erro = erro;
		this.campo = campo;
	}

	public String getCampo() {
		return campo;
	}

	public String getErro() {
		return erro;
	}
	
}
