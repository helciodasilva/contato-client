package br.com.helciodasilva.contatoclient.application.restclient;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.helciodasilva.contatoclient.domain.dto.FuncionarioDTO;

@FeignClient(url = "localhost:8080/api/v1/funcionario", name = "funcionario")
public interface FuncionarioRestClient extends CrudFeignClient<FuncionarioDTO> {

	@GetMapping
	HttpEntity<List<FuncionarioDTO>> findByNome(@RequestParam("nome") String nome);

}