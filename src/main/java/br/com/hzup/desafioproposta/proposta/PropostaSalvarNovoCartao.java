package br.com.hzup.desafioproposta.proposta;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.hzup.desafioproposta.cartao.Cartao;
import br.com.hzup.desafioproposta.externo.SolicitacoesCartao;
import feign.FeignException;

@Component
public class PropostaSalvarNovoCartao {


	@Autowired
	private SolicitacoesCartao solicitaCartao;
	
	@Transactional
	public void cadastrar(PropostaRepository propostaRep, List<Proposta> propostas) {
		try {
			propostas.forEach(proposta -> {
				Cartao cartao = solicitaCartao.gerarCartao(proposta.toCartao());
				proposta.setCartao(cartao);
			});
			propostaRep.saveAll(propostas);
		} catch (FeignException e) {}
	}
	
}
