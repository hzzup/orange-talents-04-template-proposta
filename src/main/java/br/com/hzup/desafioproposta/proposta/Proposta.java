package br.com.hzup.desafioproposta.proposta;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.hzup.desafioproposta.compartilhado.CpfOuCnpj;
import br.com.hzup.desafioproposta.externo.SolicitacaoAnaliseClient.restricoes;

@Entity
public class Proposta {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotBlank @CpfOuCnpj @Column(unique=true)
	private String cpfOuCnpj;
	@NotBlank @Email
	private String email;
	@NotBlank
	private String nome;
	@NotBlank
	private String endereco;
	@Positive @NotNull
	private BigDecimal salario;
	@Enumerated(EnumType.STRING)
	private restricoes restricao;

	// Utilizado apenas para o hibernate
	@Deprecated
	public Proposta() {}

	public Proposta(@NotBlank String cpfOuCnpj, @NotBlank @Email String email, @NotBlank String nome,
			@NotBlank String endereco, @Positive @NotNull BigDecimal salario) {
		this.cpfOuCnpj = cpfOuCnpj;
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
	}
	
	public Long getId() {
		return this.id;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setRestricao(restricoes restricao) {
		this.restricao = restricao;
	}
}
