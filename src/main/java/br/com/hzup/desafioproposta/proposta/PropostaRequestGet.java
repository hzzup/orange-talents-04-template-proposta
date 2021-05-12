package br.com.hzup.desafioproposta.proposta;

import java.math.BigDecimal;

import br.com.hzup.desafioproposta.proposta.externo.SolicitacaoAnaliseClient.restricoes;

public class PropostaRequestGet {

	private String cpfOuCnpj;
	private String email;
	private String nome;
	private String endereco;
	private BigDecimal salario;
	private restricoes restricao;
	private String cartaoId;
	
	public PropostaRequestGet(String cpfOuCnpj, String email, String nome, String endereco, BigDecimal salario,
			restricoes restricao, String cartaoId) {
		this.cpfOuCnpj = cpfOuCnpj;
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
		this.restricao = restricao;
		this.cartaoId = cartaoId;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public restricoes getRestricao() {
		return restricao;
	}

	public String getCartaoId() {
		return cartaoId;
	}
}
