package br.com.hzup.desafioproposta.cartao.externo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Id;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.hzup.desafioproposta.cartao.Cartao;

@FeignClient(url="localhost:8888", name="cartao")
public interface SolicitacoesCartao {

	@RequestMapping(method = RequestMethod.POST, value="/api/cartoes", consumes = "application/json")
	public CartaoResponse gerarCartao(@RequestBody CartaoRequest request);

	public class CartaoRequest {
		private String documento;
		private String nome;
		private String idProposta;
		
		public CartaoRequest(String documento, String nome, String idProposta) {
			this.documento = documento;
			this.nome = nome;
			this.idProposta = idProposta;
		}

		public String getDocumento() {
			return documento;
		}

		public String getNome() {
			return nome;
		}

		public String getIdProposta() {
			return idProposta;
		}
	}	
	
	public class CartaoResponse {
		@Id @NotBlank
		private String id;
		@FutureOrPresent @NotNull
		private LocalDateTime emitidoEm;
		@NotBlank
		private String titular;
		@Positive
		private BigDecimal limite;
		
		public CartaoResponse(@NotBlank String id, @FutureOrPresent @NotNull LocalDateTime emitidoEm,
				@NotBlank String titular, @Positive BigDecimal limite) {
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
		
		public Cartao toModel() {
			return new Cartao(this.id, this.emitidoEm, this.titular, this.limite);
		}
	}
}
