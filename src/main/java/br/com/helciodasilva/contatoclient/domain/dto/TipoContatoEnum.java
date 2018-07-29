package br.com.helciodasilva.contatoclient.domain.dto;

import lombok.Getter;

public enum TipoContatoEnum {

	TELEFONE("Telefone"),
	CELULAR("Celular"),
	EMAIL("E-mail");
	
	@Getter
	private String descricao;
	
	private TipoContatoEnum(String descricao) {
		this.descricao = descricao;
	}
	
	
}
