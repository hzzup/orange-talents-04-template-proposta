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
import br.com.hzup.desafioproposta.cartao.bloqueio.externo.BloquearCartao;
import br.com.hzup.desafioproposta.cartao.bloqueio.externo.BloquearCartao.SistemaResponsavel;
import feign.FeignException;

@RestController @RequestMapping("/bloqueio")
public class BloqueioCartao {

	@Autowired
	private CartaoRepository cartaoRep;
	
	@Autowired
	private BloquearCartao bloqueioLegado;
	
	@PostMapping("/{id}") @Transactional
	public ResponseEntity<?> bloquear(@PathVariable("id") String cartaoId,
			@RequestHeader(value = "User-Agent") String userAgent,
			HttpServletRequest request){
		
		BloqueioRequest bloqueioReq = new BloqueioRequest(request.getRemoteAddr(),userAgent);
		Optional<Cartao> cartaoBloquear = cartaoRep.findById(cartaoId);
		
		//return ResponseEntity.ok().build();
		if (cartaoBloquear.isEmpty()) return ResponseEntity.notFound().build();
		if (cartaoBloquear.get().getCartaoBloqueado() != null) return ResponseEntity.unprocessableEntity().build();
		
		Bloqueio bloqueioFinalizado = new Bloqueio(bloqueioReq, cartaoBloquear.get()); 
		//bloqueioRep.save(bloqueioFinalizado);
		cartaoBloquear.get().setCartaoBloqueado(bloqueioFinalizado);
		cartaoRep.save(cartaoBloquear.get());
		
		try {
			bloqueioLegado.bloqueioCartaoLegado(new SistemaResponsavel("Desafio-Proposta"),cartaoId);
		} catch (FeignException e) {}

		return ResponseEntity.ok().build();
	}
}
