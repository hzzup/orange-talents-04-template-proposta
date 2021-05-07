package br.com.hzup.desafioproposta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients
@EnableJpaRepositories(enableDefaultTransactions = false)
public class DesafioPropostaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioPropostaApplication.class, args);
	}

}
