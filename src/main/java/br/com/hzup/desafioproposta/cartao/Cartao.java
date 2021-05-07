package br.com.hzup.desafioproposta.cartao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Cartao {
	
	@Id @NotBlank
	private String id;
	@FutureOrPresent @NotNull
	private LocalDateTime emitidoEm;
	@NotBlank
	private String titular;
	@Positive
	private BigDecimal limite;
	
	//usado pelo hibernate apenas
	@Deprecated
	public Cartao() {}
	
	public Cartao(@NotBlank String id, @FutureOrPresent @NotNull LocalDateTime emitidoEm, @NotBlank String titular,
			@Positive BigDecimal limite) {
		this.id = id;
		this.emitidoEm = emitidoEm;
		this.titular = titular;
		this.limite = limite;
	}

	public String getId() {
		return id;
	}

	public LocalDateTime getEmitidoEm() {
		return emitidoEm;
	}

	public String getTitular() {
		return titular;
	}

	public BigDecimal getLimite() {
		return limite;
	}

	@Override
	public String toString() {
		return "Cartao [id=" + id + ", emitidoEm=" + emitidoEm + ", titular=" + titular + ", limite=" + limite + "]";
	}
	
	
	
}