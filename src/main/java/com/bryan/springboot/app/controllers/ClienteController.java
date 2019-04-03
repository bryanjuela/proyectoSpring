package com.bryan.springboot.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bryan.springboot.app.models.entity.Cliente;
import com.bryan.springboot.app.models.service.Service;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private Service service;
	
	@RequestMapping(value = "/listar")
	public String listar(Model model) {
		model.addAttribute("titulo","Listado de clientes");
		model.addAttribute("clientes", service.findAll());
		return "listar";
	}
	
	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model) {
		Cliente cliente= new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de cliente");
		return "form";
	}
	
	@RequestMapping(value = "/form", method=RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status) {
		//si el formulario tiene errores, vuelve a cargar el formulario  vacío
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de cliente");
			return "form";
		}
		service.save(cliente);
		status.setComplete();
		return "redirect:listar";
	}
	
	@RequestMapping(value = "/form/{id}" )
	public String editar(@PathVariable(value= "id") Long id ,Map<String, Object> model) {
		Cliente cliente= null;
		//si el id existe, lo busca
		//si no, vuelve a la pantalla de listar
		if(id> 0)
			cliente= service.findOne(id);
		else
			return "redirect:/listar";
		
		model.put("titulo", "Editar cliente");
		model.put("cliente", cliente);
		
		return "form";
	}
	
	@RequestMapping(value = "/eliminar/{id}" )
	public String eliminar(@PathVariable(value= "id") Long id) {
		if(id> 0) service.delete(id);
		return "redirect:/listar";
	}
	
}//class
