package br.com.hzup.desafioproposta.cartao.bloqueio;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.hzup.desafioproposta.cartao.Cartao;
import br.com.hzup.desafioproposta.cartao.CartaoRepository;
import br.com.hzup.desafioproposta.cartao.bloqueio.externo.BloquearCartaoClient;
import br.com.hzup.desafioproposta.cartao.bloqueio.externo.BloquearCartaoClient.SistemaResponsavel;
import feign.FeignException;

@RestController @RequestMapping("/bloqueio")
public class BloqueioController {

	@Autowired
	private CartaoRepository cartaoRep;
	
	@Autowired
	private BloquearCartaoClient bloqueioLegado;
	
	@PostMapping("/{cartaoId}") @Transactional
	public ResponseEntity<?> bloquear(@PathVariable("cartaoId") Long cartaoId,
			@RequestHeader(value = "User-Agent") String userAgent,
			HttpServletRequest request) {
		
		BloqueioRequest bloqueioReq = new BloqueioRequest(request.getRemoteAddr(),userAgent);
		Optional<Cartao> cartaoBloquear = cartaoRep.findById(cartaoId);
		
		//caso não achar o cartao retorne 404
		if (cartaoBloquear.isEmpty()) return ResponseEntity.notFound().build();
		//caso o cartao ja estiver bloqueado retorne 422
		if (cartaoBloquear.get().getCartaoBloqueado() != null) return ResponseEntity.unprocessableEntity().build();
		
		//devemos bloquear o cartao no sistema legado tambem
		try {
			//atribuir o novo bloqueio ao cartao e salvar o cartao (verificar cascade mode)
			Bloqueio bloqueioFinalizado = new Bloqueio(bloqueioReq, cartaoBloquear.get()); 
			cartaoBloquear.get().setCartaoBloqueado(bloqueioFinalizado);
			cartaoRep.save(cartaoBloquear.get());
			//enviamos uma requisicao no sistema legado para bloquear o cartao
			bloqueioLegado.bloqueioCartaoLegado(new SistemaResponsavel("Desafio-Proposta"),cartaoBloquear.get().getCartaoNro());
		} catch (FeignException e) {
			//requisicao falhou no feign
			return ResponseEntity.badRequest().build();
		}

		//se tudo funcionou, retorne ok!
		return ResponseEntity.ok().build();
	}
}
