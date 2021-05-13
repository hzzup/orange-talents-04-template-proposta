package br.com.hzup.desafioproposta.cartao.viagem;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.com.hzup.desafioproposta.cartao.Cartao;

public class ViagemRequest {

	@NotBlank
	private String destino;
	@Future @NotNull @JsonFormat(pattern="dd-MM-yyyy",shape=Shape.STRING)
	private LocalDate dataTermino;
	
	//necessario para o JSON deserializar meu objeto
	@Deprecated
	public ViagemRequest() {}
	
	public ViagemRequest(@NotBlank String destino) {
		this.destino = destino;
	}

	//necessario para o JSON, ele nao conseguia atribuir um valor
	public void setDataTermino(LocalDate dataTermino) {
		this.dataTermino = dataTermino;
	}
	
	public String getDestino() {return destino;}
	public LocalDate getDataTermino() {return dataTermino;}

	//metodo para transformar meu request na classe modelo
	public Viagem toModel(Cartao cartao, String ipCliente, String userAgent) {
		return new Viagem(destino, dataTermino, ipCliente, userAgent,cartao);
	}
}
