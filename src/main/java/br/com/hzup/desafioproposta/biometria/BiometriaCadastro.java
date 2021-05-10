package br.com.hzup.desafioproposta.biometria;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
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

@RestController @RequestMapping("/biometrias")
public class BiometriaCadastro {
	
	@Autowired
	private BiometriaRepository biometriaRep;
	
	@Autowired
	private CartaoRepository cartaoRep;

	@PostMapping("/{cartaoId}") @Transactional
	public ResponseEntity<BiometriaRequest> cadastrar(@PathVariable("cartaoId") String cartaoId,
									@RequestBody @Valid BiometriaRequest biometriaReq, UriComponentsBuilder uriBuilder) {
		
		if(!Base64.isBase64(biometriaReq.getBiometria())){
			return ResponseEntity.badRequest().build();
		}
		
		Optional<Cartao> cartao = cartaoRep.findById(cartaoId);
		
		if(cartao.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Biometria novaBiometria = biometriaReq.toModel(cartao.get());
		biometriaRep.save(novaBiometria);
		
		URI biometriaCadastrada = uriBuilder.path("/biometrias/{cartaoId}").build(novaBiometria.getId());
		return ResponseEntity.created(biometriaCadastrada).build();
	}
	
}
