package br.com.hzup.desafioproposta.proposta;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.hzup.desafioproposta.externo.SolicitacaoAnaliseClient.restricoes;

@Component
public class PropostaAssociarCartao {

	@Autowired
	PropostaRepository propostaRep;
	
	@Autowired
	PropostaSalvarNovoCartao salvarNoCartao;

	// tempo em ms
	@Scheduled(fixedDelay = 5000)
	private void criaCartaoParaProposta() {
		List<Proposta> propostas = new ArrayList<>();
		propostas = propostaRep.findByRestricaoAndCartao(restricoes.SEM_RESTRICAO, null);
		if (propostas.size() >= 1) {
			salvarNoCartao.cadastrar(propostaRep,propostas);
		}
	}
}
