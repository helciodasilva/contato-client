package br.com.helciodasilva.contatoclient.domain.dto;

import lombok.Data;

@Data
public class Contato {

	private Long id;

	private TipoContatoEnum tipoContato;

	private String descricao;

	private Funcionario funcionario;

}
