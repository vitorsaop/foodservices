package br.com.itconsulting.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.itconsulting.model.Cliente;
import br.com.itconsulting.repository.ClienteRepository;

@Controller
@RequestMapping("/clientes")
public class ClienteController {	
	

	private final ClienteRepository clienteRepository;
	private final String CLIENTE_URI = "clientes/";

	public ClienteController(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	
	@GetMapping("/")
	public ModelAndView list() {
		Iterable<Cliente> clientes = clienteRepository.findAll();
		return new ModelAndView("clientes/list", "clientes", clientes);
	}

	@GetMapping("{id}")
	public ModelAndView view(@PathVariable("id") Cliente cliente ) {
		//return new ModelAndView("clientes/view", "clientes", cliente);
		System.out.println(cliente.toString());
		return new ModelAndView(CLIENTE_URI + "view","cliente",cliente);
	}

	@GetMapping("/novo")
	public String createForm(@ModelAttribute Cliente cliente) {
		return "clientes/form";
	}

	@PostMapping(params = "form")
	public ModelAndView create(@Valid Cliente cliente, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return new ModelAndView("clientes/form", "formErrors", result.getAllErrors());
		}
		cliente = this.clienteRepository.save(cliente);
		System.out.println(cliente.toString());
		redirect.addFlashAttribute("globalMessage", "Cadastro realizado com sucesso");
		//return new ModelAndView("redirect:/"+"clientes/{cliente.id}","cliente.id", cliente.getId());
		return new ModelAndView("redirect:/" + CLIENTE_URI + "{cliente.id}","cliente.id",cliente.getId());
	}
	
	@PostMapping
	@Transactional
	public ModelAndView create(@Valid Cliente cliente) {
		clienteRepository.save(cliente);
		return new ModelAndView("redirect:/" + CLIENTE_URI + "{cliente.id}","cliente.id",cliente.getId());		
	}
	
	@GetMapping(value = "alterar/{id}")
	public ModelAndView alterarForm(@PathVariable("id") Cliente cliente) {
		return new ModelAndView("clientes/form", "cliente", cliente);
	}
	
	@GetMapping(value = "remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id, RedirectAttributes redirect) {
		this.clienteRepository.deleteById(id);
		Iterable<Cliente> clientes = this.clienteRepository.findAll();
		
		ModelAndView modelAndView = new ModelAndView("clientes/list", "clientes", clientes);
		modelAndView.addObject("globalMessage", "Cadastro removido com sucesso");
		return modelAndView;
	}
}
