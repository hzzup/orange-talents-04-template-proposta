package br.com.hzup.desafioproposta.cartao.viagem;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.hzup.desafioproposta.cartao.Cartao;
import br.com.hzup.desafioproposta.cartao.CartaoRepository;
import br.com.hzup.desafioproposta.cartao.viagem.externo.NotificaViagemLegado;
import br.com.hzup.desafioproposta.cartao.viagem.externo.NotificaViagemLegado.ViagemRequestFeign;

@RestController @RequestMapping("/viagens")
public class ViagemController {
	
	@Autowired
	CartaoRepository cartaoRep;
	
	@Autowired
	ViagemRepository viagemRep;
	
	@Autowired 
	NotificaViagemLegado viagemLegado;

	@PostMapping("/{cartaoId}") @Transactional
	public ResponseEntity<?> cadastrarViagem(@PathVariable("cartaoId") Long cartaoId,
	@RequestBody @Valid ViagemRequest viagemReq, @RequestHeader(value="User-Agent") String userAgent,HttpServletRequest request) {

		//verifico se existe o cartao, caso contrario retorno 404
		Optional<Cartao> cartao = cartaoRep.findById(cartaoId);
		if (cartao.isEmpty()) return ResponseEntity.notFound().build();
		
		try {
			//crio o modelo de viagem passando os campos necessarios para criar meu objeto e salvo
			Viagem novaViagem = viagemReq.toModel(cartao.get(), request.getRemoteAddr(), userAgent);
			viagemRep.save(novaViagem);
			viagemLegado.notificaViagemLegado(cartao.get().getCartaoNro(), new ViagemRequestFeign(viagemReq));
		} catch (Exception e) {}

		
		return ResponseEntity.ok().build();
	}
}
