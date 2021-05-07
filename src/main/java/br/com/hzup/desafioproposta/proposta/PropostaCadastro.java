package br.com.hzup.desafioproposta.proposta;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.hzup.desafioproposta.externo.SolicitacaoAnaliseClient;
import br.com.hzup.desafioproposta.externo.SolicitacaoAnaliseClient.SolicitacaoRequest;
import br.com.hzup.desafioproposta.externo.SolicitacaoAnaliseClient.restricoes;
import feign.FeignException;

@RequestMapping("/propostas") @RestController
public class PropostaCadastro {

	@Autowired
	PropostaRepository propostaRep;
	
	@Autowired
	private SolicitacaoAnaliseClient solicitacaoAnalise;
	
	@PostMapping @Transactional
	public ResponseEntity<Proposta> create(@RequestBody @Valid PropostaRequest propostaReq, UriComponentsBuilder uriBuilder) { 
		Proposta novaProposta = propostaReq.toModel();
		propostaRep.save(novaProposta);
		
		try {
			solicitacaoAnalise.verificaRestricao(new SolicitacaoRequest(novaProposta.getCpfOuCnpj(),
					novaProposta.getNome(),
					Long.toString(novaProposta.getId())));
			novaProposta.setRestricao(restricoes.SEM_RESTRICAO);
		} catch (FeignException e) {
			//retornou 4xx ou 5xx, cliente tem restricao
			novaProposta.setRestricao(restricoes.COM_RESTRICAO);
		}
		
		//propostaRep.save(novaProposta);
		
		URI enderecoCadastro = uriBuilder.path("/propostas/{id}").build(novaProposta.getId());
		return ResponseEntity.created(enderecoCadastro).build();
	}
}
