package br.com.helciodasilva.contatoclient.application.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.helciodasilva.contatoclient.application.restclient.ContatoRestClient;
import br.com.helciodasilva.contatoclient.application.restclient.FuncionarioRestClient;
import br.com.helciodasilva.contatoclient.domain.dto.ContatoDTO;
import br.com.helciodasilva.contatoclient.domain.dto.FuncionarioDTO;
import br.com.helciodasilva.contatoclient.domain.dto.TipoContatoEnum;
import feign.FeignException;

@Controller
@RequestMapping("contato")
public class ContatoController {

	@Autowired
	private ContatoRestClient contatoRestClient;

	@Autowired
	private FuncionarioRestClient funcionarioRestClient;

	@GetMapping
	public ModelAndView findAll(ContatoDTO contato) {

		ModelAndView mv = new ModelAndView("/contato");
		mv.addObject("contato", contato);

		List<ContatoDTO> contatos = contatoRestClient.findByDescicao(contato.getDescricao()).getBody();
		mv.addObject("contatos", contatos);

		return mv;
	}

	@GetMapping("/add")
	public ModelAndView add(ContatoDTO contatoDTO) {

		ModelAndView mv = new ModelAndView("/contatoAdd");
		mv.addObject("contato", contatoDTO);

		List<FuncionarioDTO> funcionarios = funcionarioRestClient.findAll().getBody();
		mv.addObject("funcionarios", funcionarios);

		TipoContatoEnum[] TiposContato = TipoContatoEnum.values();
		mv.addObject("TiposContato", TiposContato);

		return mv;
	}

	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		ContatoDTO contato = contatoRestClient.findOne(id).getBody();
		return add(contato);
	}

	@DeleteMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {

		contatoRestClient.delete(id);

		return new ModelAndView("redirect:/contato");
	}

	@PostMapping("/save")
	public ModelAndView save(@Valid ContatoDTO contatoDTO, BindingResult result, Errors errors) {

		if (result.hasErrors()) {
			return add(contatoDTO);
		}

		try {
			contatoRestClient.save(contatoDTO);
		} catch (FeignException e) {
			if (e.getMessage().contains("CONTATO_DESCRICAO_UK")) {
				errors.rejectValue("descricao", null, "A descrição já existe.");
				return add(contatoDTO);
			}
		}

		return new ModelAndView("redirect:/contato");
	}

}