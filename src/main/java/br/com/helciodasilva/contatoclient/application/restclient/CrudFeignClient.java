package br.com.helciodasilva.contatoclient.application.restclient;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public interface CrudFeignClient<T> {

	@GetMapping
	ResponseEntity<List<T>> findAll();

	@GetMapping("/{id}")
	ResponseEntity<T> findOne(@PathVariable(value = "id") Long id);

	@PostMapping
	ResponseEntity<Void> save(T entity);

	@PutMapping("/{id}")
	ResponseEntity<Void> save(@PathVariable("id") long id, T entity);

	@DeleteMapping("/{id}")
	ResponseEntity<Void> delete(@PathVariable("id") long id);
}