package com.hardcode.secretfriend.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.hardcode.secretfriend.model.Grupo;
import com.hardcode.secretfriend.repository.GrupoRepository;
import com.hardcode.secretfriend.service.GrupoService;

@RestController
@RequestMapping("/grupo")
@CrossOrigin
public class GrupoController {
	
	@Autowired
	private GrupoRepository repository;
	
	@Autowired
	private GrupoService service;
	
	@GetMapping
	public List<Grupo> findAll(){
		return repository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Grupo> findById(@PathVariable Long id) {
		Grupo grupo = repository.findById(id);
		
		if(grupo == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(grupo);
		}
		
	}
	
	@PostMapping
	public ResponseEntity<Grupo> addGrupo(@Valid @RequestBody Grupo grupo,HttpServletResponse response){
		Grupo grupoSalvo = repository.save(grupo);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(grupoSalvo.getId()).toUri();
		response.addHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(grupoSalvo);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Grupo> updateGrupo(@PathVariable Long id,@Valid @RequestBody Grupo grupo){
		
		return ResponseEntity.status(HttpStatus.OK).body(service.updateGrupo(id,grupo));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void deleteGrupo(@PathVariable Long id) {
		repository.delete(id);
	}
}
