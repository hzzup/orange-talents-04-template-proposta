package br.com.hzup.desafioproposta.cartao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.hzup.desafioproposta.cartao.externo.SolicitacoesCartaoClient;
import br.com.hzup.desafioproposta.cartao.externo.SolicitacoesCartaoClient.CartaoResponse;
import br.com.hzup.desafioproposta.proposta.Proposta;
import br.com.hzup.desafioproposta.proposta.PropostaRepository;
import feign.FeignException;
import io.opentracing.Span;
import io.opentracing.Tracer;

@Component
public class CartaoNovo {

	private final Tracer tracer;
	
	public CartaoNovo(Tracer tracer) {
		this.tracer = tracer;
	}
	
	@Autowired
	private SolicitacoesCartaoClient solicitaCartao;
	
	@Transactional
	public void cadastrar(PropostaRepository propostaRep, List<Proposta> propostas) {
		try {
			//para cada proposta deve ser tentado cadastrar um novo cartao pelo feign "cartao"
			propostas.forEach(proposta -> {
				//passando baggage item dentro do meu scheduler
				Span activeSpan = tracer.activeSpan();
				activeSpan.setBaggageItem("id.proposta.cartao", proposta.getId().toString());
				//CartaoResponse cartaoResponse = solicitaCartao.receberCartao(proposta.getId().toString());
				CartaoResponse cartaoResponse = solicitaCartao.receberCartao(proposta.getId());
				Cartao cartao = cartaoResponse.toModel();
				proposta.setCartao(cartao);
			});
			propostaRep.saveAll(propostas);
		} catch (FeignException e) {}
	}
}
