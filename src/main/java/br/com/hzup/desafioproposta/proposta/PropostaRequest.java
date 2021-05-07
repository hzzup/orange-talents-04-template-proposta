package br.com.hzup.desafioproposta.proposta;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.hzup.desafioproposta.compartilhado.CpfOuCnpj;
import br.com.hzup.desafioproposta.compartilhado.ValorUnicoNoBanco;

public class PropostaRequest {

	@NotBlank @CpfOuCnpj @ValorUnicoNoBanco(tabela=Proposta.class, campo="cpfOuCnpj")
	private String cpfOuCnpj;
	@NotBlank @Email
	private String email;
	@NotBlank
	private String nome;
	@NotBlank
	private String endereco;
	@Positive @NotNull
	private BigDecimal salario;
	
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
	
	public Proposta toModel() {
		return new Proposta(cpfOuCnpj, email, nome, endereco, salario);
	}
}
