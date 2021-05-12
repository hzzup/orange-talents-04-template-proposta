package br.com.hzup.desafioproposta.cartao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends CrudRepository<Cartao,String>{

	public Optional<Cartao> findByCartaoNro(String cartaoNro);
	
}
