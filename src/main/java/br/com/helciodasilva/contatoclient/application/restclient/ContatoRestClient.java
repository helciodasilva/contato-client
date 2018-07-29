package br.com.helciodasilva.contatoclient.application.restclient;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.helciodasilva.contatoclient.domain.dto.ContatoDTO;
import br.com.helciodasilva.contatoclient.domain.dto.FuncionarioExistsDTO;

@FeignClient(url = "localhost:8080/api/v1/contato", name = "contato")
public interface ContatoRestClient extends CrudFeignClient<ContatoDTO> {

	@GetMapping("/funcionario/{id}/exists")
	ResponseEntity<FuncionarioExistsDTO> existsByFuncionarioId(@PathVariable(value = "id") Long id);

	@GetMapping
	HttpEntity<List<ContatoDTO>> findByDescicao(@RequestParam("descricao") String descricao);

}