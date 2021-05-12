package br.com.hzup.desafioproposta.viagem;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.com.hzup.desafioproposta.cartao.Cartao;

@Entity
public class Viagem {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String destino;
	@Future @NotNull @JsonFormat(pattern="dd-MM-yyyy",shape=Shape.STRING)
	private LocalDate dataTermino;
	@NotNull
	private LocalDateTime instanteAviso = LocalDateTime.now();
	@NotBlank
	private String ipCliente;
	@NotBlank
	private String userAgent;
	@ManyToOne @NotNull
	private Cartao cartao;
	
	public Viagem(@NotBlank String destino, @Future @NotNull LocalDate termino,
			@NotBlank String ipCliente,	@NotBlank String userAgent, @NotNull Cartao cartao) {
		this.destino = destino;
		this.dataTermino = termino;
		this.ipCliente = ipCliente;
		this.userAgent = userAgent;
		this.cartao = cartao;
	}
}
