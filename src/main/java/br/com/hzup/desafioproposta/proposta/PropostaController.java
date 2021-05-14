package br.com.hzup.desafioproposta.proposta;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.hzup.desafioproposta.compartilhado.metricas.MinhasMetricas;
import br.com.hzup.desafioproposta.proposta.externo.SolicitacaoAnaliseClient;
import br.com.hzup.desafioproposta.proposta.externo.SolicitacaoAnaliseClient.restricoes;
import feign.FeignException;
import io.opentracing.Span;
import io.opentracing.Tracer;

@RequestMapping("/propostas") @RestController
public class PropostaController {

	private final Tracer tracer;
	
	public PropostaController(Tracer tracer) {
		this.tracer = tracer;
	}
	
	//Inserindo a minha classe de metricas
	@Autowired
	private MinhasMetricas metricas;

	//repository para salvar minha proposta
	@Autowired
	private PropostaRepository propostaRep;
	
	//classe que irá conversar com um cliente feign externo no caso o cliente "analise"
	@Autowired
	private SolicitacaoAnaliseClient solicitacaoAnalise;
	
	//gerar uma nova proposta e verificar a restricao
	@PostMapping @Transactional
	public ResponseEntity<?> create(@RequestBody @Valid PropostaRequest propostaReq, UriComponentsBuilder uriBuilder) { 
		Proposta novaProposta = propostaReq.toModel();
		propostaRep.save(novaProposta);
		//testando spans open tracing
		Span activeSpan = tracer.activeSpan();
		activeSpan.setTag("user.id", novaProposta.getId());
		try {
			solicitacaoAnalise.verificaRestricao(novaProposta.toSolicitacao());
			novaProposta.setRestricao(restricoes.SEM_RESTRICAO);
		} catch (FeignException e) {
			//Apenas possui restricao caso seja devolvido 422
			if(e.status()==422)	novaProposta.setRestricao(restricoes.COM_RESTRICAO);
			else throw e;
		}
		//metrica para verificar quantos clientes possuem restricao e quantos nao possuem.
		metricas.metricaCounter(novaProposta.getRestricao());
		URI enderecoCadastro = uriBuilder.path("/propostas/{id}").build(novaProposta.getId());
		return ResponseEntity.created(enderecoCadastro).build();
	}
	
	//pegar uma proposta por id e retornar os dados
	@GetMapping("/{id}")
	public ResponseEntity<PropostaResponse> listarPorId(@PathVariable("id") Long id) {
		//metrica para verificar quanto tempo para retornar uma busca de cliente
		//Timer meuTimer = metricas.metricaTimer();
		//meuTimer.record(() -> {
			Optional<Proposta> propostaCadastrada = propostaRep.findById(id);

			if (propostaCadastrada.isPresent()) {
				PropostaResponse propostaEncontrada = propostaCadastrada.get().toPropostaResponse();
				return ResponseEntity.ok(propostaEncontrada);
			}

			return ResponseEntity.notFound().build();
		//});
	}
}
