package br.com.hzup.desafioproposta.externo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.hzup.desafioproposta.cartao.Cartao;

@FeignClient(url="localhost:8888", name="cartao")
public interface SolicitacoesCartao {

	@RequestMapping(method = RequestMethod.POST, value="/api/cartoes", consumes = "application/json")
	public Cartao gerarCartao(CartaoRequest request);

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
}
