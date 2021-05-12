package br.com.hzup.desafioproposta.cartao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.hzup.desafioproposta.proposta.Proposta;
import br.com.hzup.desafioproposta.proposta.PropostaRepository;
import br.com.hzup.desafioproposta.proposta.externo.SolicitacaoAnaliseClient.restricoes;

@Component
public class CartaoAssociarProposta {

	@Autowired
	PropostaRepository propostaRep;
	
	@Autowired
	CartaoNovo salvarNoCartao;

	// tempo em ms
	@Scheduled(fixedDelay = 10000)
	public void criaCartaoParaProposta() {
		//procurar por propostas sem cartao e sem restricao
		List<Proposta> propostas = new ArrayList<>();
		propostas = propostaRep.findByRestricaoAndCartao(restricoes.SEM_RESTRICAO, null);
		if (propostas.size() >= 1) {
			salvarNoCartao.cadastrar(propostaRep,propostas);
		}
	}
}
