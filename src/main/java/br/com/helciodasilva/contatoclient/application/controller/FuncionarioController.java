package br.com.helciodasilva.contatoclient.application.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.helciodasilva.contatoclient.application.restclient.ContatoRestClient;
import br.com.helciodasilva.contatoclient.application.restclient.FuncionarioRestClient;
import br.com.helciodasilva.contatoclient.domain.dto.FuncionarioDTO;
import br.com.helciodasilva.contatoclient.domain.dto.FuncionarioExistsDTO;

@Controller
@RequestMapping("funcionario")
public class FuncionarioController {

	@Autowired
	private FuncionarioRestClient funcionarioRestClient;
	
	@Autowired
	private ContatoRestClient contatoRestClient;

	@GetMapping
	public ModelAndView findAll(FuncionarioDTO funcionario) {

		ModelAndView mv = new ModelAndView("/funcionario");
		List<FuncionarioDTO> funcionarios = funcionarioRestClient.findByNome(funcionario.getNome()).getBody();
		mv.addObject("funcionario", funcionario);
		mv.addObject("funcionarios", funcionarios);

		return mv;
	}

	@GetMapping("/add")
	public ModelAndView add(FuncionarioDTO funcionarioDTO) {

		ModelAndView mv = new ModelAndView("/funcionarioAdd");
		mv.addObject("funcionario", funcionarioDTO);

		return mv;
	}

	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		FuncionarioDTO funcionario = funcionarioRestClient.findOne(id).getBody();
		return add(funcionario);
	}

	@DeleteMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id, RedirectAttributes attributes) {

		FuncionarioExistsDTO funcionarioExists = contatoRestClient.existsByFuncionarioId(id).getBody();

		if (funcionarioExists.isExists()) {
			attributes.addFlashAttribute("erro", "Não foi possível excluir o Funcionario. Ele está vinculado à um contato");
			return new ModelAndView("redirect:/funcionario");
		}

		funcionarioRestClient.delete(id);
		return new ModelAndView("redirect:/funcionario");
	}

	@PostMapping("/save")
	public ModelAndView save(@Valid FuncionarioDTO funcionarioDTO, BindingResult result) {

		if (result.hasErrors()) {
			return add(funcionarioDTO);
		}

		funcionarioRestClient.save(funcionarioDTO);

		return new ModelAndView("redirect:/funcionario");
	}

}