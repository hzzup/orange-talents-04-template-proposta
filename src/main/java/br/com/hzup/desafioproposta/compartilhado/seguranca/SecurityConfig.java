package br.com.hzup.desafioproposta.compartilhado.seguranca;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(authorizeRequests -> authorizeRequests
				.antMatchers(HttpMethod.GET, "/propostas/**").hasAuthority("SCOPE_acesso_proposta")
				.antMatchers(HttpMethod.GET, "/api/cartoes/**").hasAuthority("SCOPE_acesso_cartao")
				.antMatchers(HttpMethod.POST, "/api/cartoes/**").hasAuthority("SCOPE_acesso_proposta")
				.antMatchers(HttpMethod.POST, "/propostas/**").hasAuthority("SCOPE_sistema_geral")
				.antMatchers(HttpMethod.POST, "/bloqueio/**").hasAuthority("SCOPE_acesso_cartao")
				.antMatchers(HttpMethod.POST, "/viagens/**").hasAuthority("SCOPE_acesso_cartao")
				.antMatchers("/actuator/**").hasAuthority("SCOPE_acesso_metricas")
				.anyRequest().authenticated())
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
	}
}
