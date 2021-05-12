package br.com.hzup.desafioproposta.cartao.externo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.hzup.desafioproposta.cartao.Cartao;

@FeignClient(url="${cartoes.host}", name="cartao")
public interface SolicitacoesCartao {

	@RequestMapping(method = RequestMethod.POST,value="/api/cartoes", consumes = "application/json")
	public CartaoResponse gerarCartao(@RequestBody CartaoRequest request);

	//classe para ser enviada como requisicao da minha api externa feign client "cartao"
	public class CartaoRequest {
		private String documento;
		private String nome;
		private Long idProposta;
		
		public CartaoRequest(String documento, String nome, Long idProposta) {
			this.documento = documento;
			this.nome = nome;
			this.idProposta = idProposta;
		}

		public String getDocumento() {return documento;}
		public String getNome() {return nome;}
		public Long getIdProposta() {return idProposta;}
	}	
	
	//classe de reposta recebia pelo meu feign client "cartao"
	public class CartaoResponse {
		private String id;
		private LocalDateTime emitidoEm;
		private BigDecimal limite;
		
		public CartaoResponse(String id, LocalDateTime emitidoEm, BigDecimal limite) {
			this.id = id;
			this.emitidoEm = emitidoEm;
			this.limite = limite;
		}

		public String getId() {return id;}
		public LocalDateTime getEmitidoEm() {return emitidoEm;}
		public BigDecimal getLimite() {return limite;}
		
		//precisamos devolver no molde de cartao para poder persistir no banco
		public Cartao toModel() {
			return new Cartao(this.id, this.emitidoEm, this.limite);
		}
	}
}
