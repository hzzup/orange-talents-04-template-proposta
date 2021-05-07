package br.com.hzup.desafioproposta.proposta;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.hzup.desafioproposta.cartao.Cartao;
import br.com.hzup.desafioproposta.externo.SolicitacaoAnaliseClient.restricoes;

public interface PropostaRepository extends CrudRepository<Proposta,Long>{

	public List<Proposta> findByRestricaoAndCartao(restricoes restricao, Cartao cartao);
	
}
