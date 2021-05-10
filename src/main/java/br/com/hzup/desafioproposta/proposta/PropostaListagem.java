package br.com.hzup.desafioproposta.proposta;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/propostas") @RestController
public class PropostaListagem {

	@Autowired
	private PropostaRepository propostaRep;
	
	@GetMapping("/{id}")
	public ResponseEntity<PropostaRequestGet> listarPorId(@PathVariable Long id) {
		Optional<Proposta> propostaCadastrada = propostaRep.findById(id);
		
		if (propostaCadastrada.isPresent()) {
			PropostaRequestGet propostaEncontrada = propostaCadastrada.get().toRequestGet();
			return ResponseEntity.ok(propostaEncontrada);
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
