package com.bryan.springboot.app.controllers;

import java.util.Calendar; 
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bryan.springboot.app.models.entity.Cliente;
import com.bryan.springboot.app.models.service.Service;
import com.bryan.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private Service service;
	
	//metodo de prueba
	@RequestMapping(value= "/get_one/{id}", method= RequestMethod.GET)
	public ResponseEntity<?> getOne(@PathVariable("id") final Long id){
		
		if(id!= null && id> 0) {
			Cliente cliente= service.findOne(id);
			if(cliente!= null)
				return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	/*Comentario de prueba*/
	
	@RequestMapping(value = "/listar")
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageable= PageRequest.of(page, 5);
		Page<Cliente> clientes= service.findAll(pageable);
		PageRender<Cliente> pageRender= new PageRender<>("/listar", clientes);
		model.addAttribute("titulo","Listado de clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
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
		Calendar calendar= Calendar.getInstance();
		calendar.setTime(cliente.getCreateAt());
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)+1);
		cliente.setCreateAt(calendar.getTime());
		
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
