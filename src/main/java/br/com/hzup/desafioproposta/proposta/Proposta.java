package br.com.hzup.desafioproposta.proposta;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.hzup.desafioproposta.cartao.Cartao;
import br.com.hzup.desafioproposta.cartao.externo.SolicitacoesCartao.CartaoRequest;
import br.com.hzup.desafioproposta.compartilhado.validacao.CpfOuCnpj;
import br.com.hzup.desafioproposta.proposta.externo.SolicitacaoAnaliseClient.SolicitacaoRequest;
import br.com.hzup.desafioproposta.proposta.externo.SolicitacaoAnaliseClient.restricoes;

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
	@OneToOne(cascade = CascadeType.MERGE)
	private Cartao cartao;

	// Utilizado apenas para o hibernate
	@Deprecated
	public Proposta() {}

	//Construtor com os campos obrigatórios
	public Proposta(@NotBlank String cpfOuCnpj, @NotBlank @Email String email, @NotBlank String nome,
			@NotBlank String endereco, @Positive @NotNull BigDecimal salario) {
		this.cpfOuCnpj = cpfOuCnpj;
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
	}
	
	public Long getId() {return this.id;}
	public restricoes getRestricao() {return restricao;}

	//restricao precisa ser setada na mao, por isso um setter
	public void setRestricao(restricoes restricao) {
		this.restricao = restricao;
	}
	
	//cartao tambem necessita ser settado na mao por ser gerado em outro sistema
	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}
	

	//transformar minha proposta no formato de solicitacao(request) para o cliente feign "analise"
	//para verificar a restricao do cliente
	//passar documento - nome - id (tudo em formato de string)
	public SolicitacaoRequest toSolicitacao() {
		return new SolicitacaoRequest(this.cpfOuCnpj,this.nome,Long.toString(this.id));
	}

	//transformar minha proposta no formato de solicitacao(request) para o cliente feign "cartao"
	//para gerar um novo cartão para meu cliente
	//passar documento - nome - id (tudo em formato de string)
	public CartaoRequest toCartaoRequest() {
		return new CartaoRequest(this.cpfOuCnpj,this.nome,Long.toString(this.id));
	}

	//Transformar minha proposta no formato de modelo necessario para a devolucao de dados
	//de um metodo do tipo GET, resumindo, os dados que devemos retornar como resposta http
	//aqui verificamos tambem se o cliente possui um cartao ou nao.
	public PropostaResponse toPropostaResponse() {
		String cartaoNro = null;
		if (this.cartao != null) {
			cartaoNro = this.cartao.getCartaoNro();
		}
		return new PropostaResponse(this.cpfOuCnpj,this.email,this.nome,this.endereco,
								this.salario,this.restricao,cartaoNro);
	}
}
