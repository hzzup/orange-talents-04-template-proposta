package br.com.hzup.desafioproposta.proposta;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/propostas") @RestController
public class PropostaCadastro {

	@Autowired
	PropostaRepository propostaRep;
	
	@PostMapping
	public ResponseEntity<Proposta> create(@RequestBody @Valid PropostaRequest propostaReq, UriComponentsBuilder uriBuilder) { 
		Proposta novaProposta = propostaReq.toModel();
		propostaRep.save(novaProposta);
		
		URI enderecoCadastro = uriBuilder.path("/propostas/{id}").build(novaProposta.getId());
		return ResponseEntity.created(enderecoCadastro).build();
	}
}
