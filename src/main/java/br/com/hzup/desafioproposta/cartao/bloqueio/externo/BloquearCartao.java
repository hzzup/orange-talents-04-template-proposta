package br.com.hzup.desafioproposta.cartao.bloqueio.externo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url="${cartoes.host}", name="cartaoBlock")
public interface BloquearCartao {

	@RequestMapping(method = RequestMethod.POST, value="/api/cartoes/{id}/bloqueios",consumes="application/json", produces = "application/json")
	public String bloqueioCartaoLegado(@RequestBody SistemaResponsavel sistemaResponsavel,
			@PathVariable("id") String cartaoNro);

	//precisamos criar uma classe para passar o sistema responsavel pelo bloqueio
	public class SistemaResponsavel{
		
		private String sistemaResponsavel;
		
		public SistemaResponsavel(String sistemaResponsavel) {
			this.sistemaResponsavel = sistemaResponsavel;
		}
		
		public String getSistemaResponsavel() {return sistemaResponsavel;}
	}
}