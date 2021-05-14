package br.com.hzup.desafioproposta.compartilhado.metricas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.hzup.desafioproposta.proposta.externo.SolicitacaoAnaliseClient.restricoes;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;

@Component
public class MinhasMetricas {
	private final MeterRegistry meterRegistry;
    private final Collection<String> strings = new ArrayList<>();
    private final Random random = new Random();

	public MinhasMetricas(MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
		metricaGauge();
	}
	
	public void metricaCounter(restricoes restricao) {
	    Collection<Tag> tags = new ArrayList<>();
	    tags.add(Tag.of("restricao", restricao.toString()));

	    Counter contadorDePropostasCriadas = this.meterRegistry.counter("proposta_criada", tags);
	    contadorDePropostasCriadas.increment();
	}
	
	public Timer metricaTimer() {
		Collection<Tag> tags = new ArrayList<>();
	    tags.add(Tag.of("cliente", "consulta"));

		Timer timerConsultarProposta = this.meterRegistry.timer("consultar_proposta", tags);
		return timerConsultarProposta;
		//timerConsultarProposta.record(() -> {
		//});
	}
	
    public void metricaGauge() {
        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("desafio", "proposta"));

        this.meterRegistry.gauge("meu_gauge", tags, strings, Collection::size);
    }
    
    public void removeString() {
        strings.removeIf(Objects::nonNull);
    }

    public void addString() {
        strings.add(UUID.randomUUID().toString());
    }
    
    @Scheduled(fixedDelay = 60000)
    public void simulandoGauge() {
        double randomNumber = random.nextInt();
        if (randomNumber % 2 == 0) {
            addString();
        } else {
            removeString();
        }
    }
}