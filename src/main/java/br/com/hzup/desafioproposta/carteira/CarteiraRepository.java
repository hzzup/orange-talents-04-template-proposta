package br.com.hzup.desafioproposta.carteira;

import org.springframework.data.repository.CrudRepository;

import br.com.hzup.desafioproposta.cartao.Cartao;
import br.com.hzup.desafioproposta.carteira.CarteiraRequest.tipo_carteira;
import br.com.hzup.desafioproposta.carteira.externo.CarteiraLegadoClient.associado_legado;

public interface CarteiraRepository extends CrudRepository<Carteira,Long>{

	public boolean existsByCartaoAndCarteiraAndAssociado(Cartao cartao, tipo_carteira tipoCarteira, associado_legado associacao);
	
}
