package com.hardcode.secretfriend.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hardcode.secretfriend.model.Contato;
import com.hardcode.secretfriend.repository.ContatoRepository;
import com.hardcode.secretfriend.service.ContatoService;

@RestController
@RequestMapping("/contato")
public class ContatoController {

	@Autowired
	private ContatoRepository repository;
	
	@Autowired
	private ContatoService service;
	
	@GetMapping
	public List<Contato> findAll(){
		return repository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Contato> findById(@PathVariable Long id) {
		Contato contato = repository.findById(id);
		if(contato == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Contato>(contato, HttpStatus.OK);
		}
	}
	
	@PostMapping
	public ResponseEntity<Contato> addContato(@Valid @RequestBody Contato contato, HttpServletResponse respose){
		Contato contatoSalvo = repository.save(contato);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(contato.getId()).toUri();
		respose.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(contatoSalvo);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Contato> updateContato(@Valid @RequestBody Contato contato, @PathVariable Long id){
		return new ResponseEntity<Contato>(service.updateContato(id, contato),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void deleteContato(@PathVariable Long id){
		repository.delete(id);
	}
	
}
