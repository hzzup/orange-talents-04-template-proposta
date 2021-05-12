package br.com.hzup.desafioproposta.cartao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.hzup.desafioproposta.cartao.bloqueio.Bloqueio;

@Entity
public class Cartao {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String cartaoNro;
	@NotNull
	private LocalDateTime emitidoEm;
	@Positive
	private BigDecimal limite;
	@OneToOne(cascade=CascadeType.ALL)
	private Bloqueio cartaoBloqueado;
	
	//usado pelo hibernate apenas
	@Deprecated
	public Cartao() {}
	
	public Cartao(@NotBlank String cartaoNro, @NotNull LocalDateTime emitidoEm,	@Positive BigDecimal limite) {
		this.cartaoNro = cartaoNro;
		this.emitidoEm = emitidoEm;
		this.limite = limite;
	}

	public Long getId() {return id;}
	public String getCartaoNro() {return cartaoNro;}
	public Bloqueio getCartaoBloqueado() {return cartaoBloqueado;}
	public LocalDateTime getEmitidoEm() {return emitidoEm;}
	public BigDecimal getLimite() {return limite;}

	//necessario metodo set para o bloqueio, pois vem de api externa feign cliente(cartaoBlock)
	public void setCartaoBloqueado(Bloqueio cartaoBloqueado) {
		this.cartaoBloqueado = cartaoBloqueado;
	}
}