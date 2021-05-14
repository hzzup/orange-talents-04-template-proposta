package br.com.hzup.desafioproposta.carteira;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.hzup.desafioproposta.cartao.Cartao;
import br.com.hzup.desafioproposta.cartao.CartaoRepository;
import br.com.hzup.desafioproposta.carteira.externo.CarteiraLegadoClient;
import br.com.hzup.desafioproposta.carteira.externo.CarteiraLegadoClient.CarteiraResponse;
import br.com.hzup.desafioproposta.carteira.externo.CarteiraLegadoClient.associado_legado;
import feign.FeignException;

@RestController @RequestMapping("/carteiras")
public class CarteiraController {

	@Autowired
	private CartaoRepository cartaoRep;
	
	@Autowired
	private CarteiraRepository carteiraRep;
	
	@Autowired
	private CarteiraLegadoClient associarCarteiraLegado;
	
	@PostMapping("/{id}") @Transactional
	public ResponseEntity<?> cadastrarCarteira(@PathVariable("id") Long cartaoId, 
			@RequestBody @Valid CarteiraRequest carteiraReq,
			UriComponentsBuilder uriBuilder) {
		
		Optional<Cartao> cartaoEncontrado = cartaoRep.findById(cartaoId);
		
		//caso o cartao nao exista 404
		if (cartaoEncontrado.isEmpty()) return ResponseEntity.notFound().build();
		//caso o cartao ja tenha uma carteira do tipo passado associado tanto no nosso quanto no legado 422
		if (carteiraRep.existsByCartaoAndCarteiraAndAssociado(cartaoEncontrado.get(), carteiraReq.getCarteira(), associado_legado.ASSOCIADA)) {
			return ResponseEntity.unprocessableEntity().build();
		}
		
		try {
			CarteiraResponse respostaLegado = associarCarteiraLegado.gerarCarteira(cartaoEncontrado.get().getCartaoNro(), carteiraReq);
			Carteira carteiraNova = carteiraReq.toModel(respostaLegado, cartaoEncontrado.get());
			carteiraRep.save(carteiraNova);
			URI carteiraCadastrada = uriBuilder.path("/carteiras/{id}").build(carteiraNova.getId());
			return ResponseEntity.created(carteiraCadastrada).build();
		} catch (FeignException e) {return ResponseEntity.badRequest().build();}
	}
}
