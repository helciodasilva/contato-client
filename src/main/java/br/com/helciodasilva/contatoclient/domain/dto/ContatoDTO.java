package br.com.helciodasilva.contatoclient.domain.dto;

import lombok.Data;

@Data
public class ContatoDTO {

	private Long id;

	private TipoContatoEnum tipoContato;

	private String descricao;

	private FuncionarioDTO funcionario;

}
