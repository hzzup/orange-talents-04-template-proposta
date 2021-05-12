package br.com.hzup.desafioproposta.compartilhado.erro;

import java.util.Collection;

//classe para trabalhar o erro recebido na excecao
public class ErroPadrao {

	private Collection<String> mensagens;

	public ErroPadrao(Collection<String> mensagens) {
		this.mensagens = mensagens;
	}

	public Collection<String> getMensagens() {return mensagens;}
}
