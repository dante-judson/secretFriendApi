package com.hardcode.secretfriend.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

import com.hardcode.secretfriend.exception.UserNotAuthorizedException;
import com.hardcode.secretfriend.model.Grupo;
import com.hardcode.secretfriend.repository.GrupoRepository;
import com.hardcode.secretfriend.repository.UsuarioRepository;
import com.hardcode.secretfriend.service.GrupoService;
import com.hardcode.secretfriend.util.TokenUtil;

@RestController
@RequestMapping("/grupo")
public class GrupoController {
	
	@Autowired
	private GrupoRepository repository;
	
	@Autowired
	private UsuarioRepository userRepository;
	
	@Autowired
	private GrupoService service;
	
	@GetMapping
	public List<Grupo> findAll(HttpServletRequest request){
		String username = TokenUtil.getUsernameFromHeader(request.getHeader("Authorization"));
		return repository.findByUsuario(userRepository.findByUsername(username));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Grupo> findById(@PathVariable Long id, HttpServletRequest request) throws UserNotAuthorizedException {
		Grupo grupo = repository.findById(id);
		String username = TokenUtil.getUsernameFromHeader(request.getHeader("Authorization"));
		
		if(grupo == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} 
		else if(!grupo.getUsuario().getUsername().equals(username)){
			throw new UserNotAuthorizedException();
		} else	{
			return ResponseEntity.status(HttpStatus.OK).body(grupo);
		}
		
	}
	
	@PostMapping
	public ResponseEntity<Grupo> addGrupo(@Valid @RequestBody Grupo grupo,HttpServletResponse response, HttpServletRequest request){
		String username = TokenUtil.getUsernameFromHeader(request.getHeader("Authorization"));
		
		grupo.setUsuario(userRepository.findByUsername(username));
		Grupo grupoSalvo = repository.save(grupo);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(grupoSalvo.getId()).toUri();
		response.addHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(grupoSalvo);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Grupo> updateGrupo(@PathVariable Long id,@Valid @RequestBody Grupo grupo, HttpServletRequest request) throws EmptyResultDataAccessException, UserNotAuthorizedException{
		String username = TokenUtil.getUsernameFromHeader(request.getHeader("Authorization"));
		return ResponseEntity.status(HttpStatus.OK).body(service.updateGrupo(id,grupo,username));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void deleteGrupo(@PathVariable Long id, HttpServletRequest request) throws UserNotAuthorizedException {
		String username = TokenUtil.getUsernameFromHeader(request.getHeader("Authorization"));
		Grupo grupo = repository.findById(id);
		
		if(grupo != null && grupo.getUsuario().getUsername().equals(username)) {
			throw new UserNotAuthorizedException();
		}
		repository.delete(id);
	}
}
