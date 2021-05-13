package br.com.hzup.desafioproposta.cartao.viagem;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViagemRepository extends CrudRepository<Viagem,Long> {

}
