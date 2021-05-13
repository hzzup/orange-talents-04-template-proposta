package br.com.hzup.desafioproposta.cartao.viagem.externo;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.hzup.desafioproposta.cartao.viagem.ViagemRequest;

@FeignClient(url="${cartoes.host}", name="notificaViagem")
public interface NotificaViagemLegado {

	@PostMapping("/api/cartoes/{id}/avisos")
	public String notificaViagemLegado(@PathVariable("id") String cartaoNro, @RequestBody ViagemRequestFeign viagemReqFei);

	public class ViagemRequestFeign {
		
		private String destino;
		private LocalDate validoAte;
		
		public String getDestino() {return destino;}
		public LocalDate getValidoAte() {return validoAte;}

		public ViagemRequestFeign(@Valid ViagemRequest viagemReq) {
			destino = viagemReq.getDestino();
			validoAte = viagemReq.getDataTermino();
		}
	}
}
