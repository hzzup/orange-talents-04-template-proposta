package br.com.hzup.desafioproposta.cartao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.hzup.desafioproposta.cartao.externo.SolicitacoesCartao;
import br.com.hzup.desafioproposta.cartao.externo.SolicitacoesCartao.CartaoResponse;
import br.com.hzup.desafioproposta.proposta.Proposta;
import br.com.hzup.desafioproposta.proposta.PropostaRepository;
import feign.FeignException;

@Component
public class CartaoNovo {

	@Autowired
	private SolicitacoesCartao solicitaCartao;
	
	@Transactional
	public void cadastrar(PropostaRepository propostaRep, List<Proposta> propostas) {
		try {
			//para cada proposta deve ser tentado cadastrar um novo cartao pelo feign "cartao"
			propostas.forEach(proposta -> {
				CartaoResponse cartaoResponse = solicitaCartao.gerarCartao(proposta.toCartaoRequest());
				Cartao cartao = cartaoResponse.toModel();
				proposta.setCartao(cartao);
			});
			propostaRep.saveAll(propostas);
		} catch (FeignException e) {}
	}
}
