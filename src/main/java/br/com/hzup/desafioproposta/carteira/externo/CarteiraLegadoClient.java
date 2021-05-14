package br.com.hzup.desafioproposta.carteira.externo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.hzup.desafioproposta.carteira.CarteiraRequest;

@FeignClient(url="${cartoes.host}", name="carteira")
public interface CarteiraLegadoClient {

	@PostMapping("api/cartoes/{id}/carteiras")
	public CarteiraResponse gerarCarteira(@PathVariable("id") String cartaoNro ,@RequestBody CarteiraRequest carteiraReq);
	
	public class CarteiraResponse {
		private String id;
		private associado_legado resultado;
		
		public String getId() {return id;}
		public associado_legado getResultado() {return resultado;}
		
		public CarteiraResponse(String id, associado_legado resultado) {
			this.id = id;
			this.resultado = resultado;
		}
	}
	
	public enum associado_legado {
		ASSOCIADA, FALHA
	}
}
