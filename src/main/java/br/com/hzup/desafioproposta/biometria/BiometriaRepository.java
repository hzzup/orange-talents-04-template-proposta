package br.com.hzup.desafioproposta.biometria;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiometriaRepository extends CrudRepository<Biometria,Long>{

}
