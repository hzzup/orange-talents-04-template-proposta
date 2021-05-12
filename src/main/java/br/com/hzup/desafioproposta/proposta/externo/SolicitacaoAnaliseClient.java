package br.com.hzup.desafioproposta.proposta.externo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url="${analise.host}", name="analise")
public interface SolicitacaoAnaliseClient {

	@RequestMapping(method = RequestMethod.POST,value="/api/solicitacao", consumes = "application/json")
	public SolicitacaoResponse verificaRestricao(SolicitacaoRequest request);

	//minha classe de requisicao para o cliente feign
	public class SolicitacaoRequest {
		private String documento;
		private String nome;
		private String idProposta;
		
		public SolicitacaoRequest(String documento, String nome, String idProposta) {
			this.documento = documento;
			this.nome = nome;
			this.idProposta = idProposta;
		}

		public String getDocumento() {return documento;}
		public String getNome() {return nome;}
		public String getIdProposta() {return idProposta;}
	}
	
	//minha classe de resposta vinda do cliente feign
	public class SolicitacaoResponse {
		private String documento;
		private String nome;
		private String idProposta;
		private restricoes restricaoAtual;
		
		public SolicitacaoResponse(String documento, String nome, String idProposta, restricoes restricaoAtual) {
			this.documento = documento;
			this.nome = nome;
			this.idProposta = idProposta;
			this.restricaoAtual = restricaoAtual;
		}

		public String getDocumento() {return documento;}
		public String getNome() {return nome;}
		public String getIdProposta() {return idProposta;}
		public restricoes getRestricaoAtual() {return restricaoAtual;}
		
	}
	
	//meu enum que devolve o tipo de restricao do cliente
	public enum restricoes {
		COM_RESTRICAO,
		SEM_RESTRICAO;
	}
}
