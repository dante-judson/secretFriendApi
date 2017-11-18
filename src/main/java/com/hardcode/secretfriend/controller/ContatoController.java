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
import com.hardcode.secretfriend.model.Contato;
import com.hardcode.secretfriend.repository.ContatoRepository;
import com.hardcode.secretfriend.repository.UsuarioRepository;
import com.hardcode.secretfriend.service.ContatoService;
import com.hardcode.secretfriend.util.TokenUtil;

@RestController
@RequestMapping("/contato")
public class ContatoController {

	@Autowired
	private ContatoRepository repository;
	
	@Autowired
	private UsuarioRepository userRepository;
	
	@Autowired
	private ContatoService service;
	
	@GetMapping
	public List<Contato> findAll(HttpServletRequest request){
		String username = TokenUtil.getUsernameFromHeader(request.getHeader("Authorization"));
		return repository.findByUsuario(userRepository.findByUsername(username));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Contato> findById(@PathVariable Long id,HttpServletRequest request) throws UserNotAuthorizedException {
		Contato contato = repository.findById(id);
		String username = TokenUtil.getUsernameFromHeader(request.getHeader("Authorization"));
		
		if(contato == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} 
		else if(!contato.getUsuario().getUsername().equals(username)) {
			throw new UserNotAuthorizedException();
		}
		else {
			return new ResponseEntity<Contato>(contato, HttpStatus.OK);
		}
	}
	
	@PostMapping
	public ResponseEntity<Contato> addContato(@Valid @RequestBody Contato contato, HttpServletResponse respose,HttpServletRequest request){
		String username = TokenUtil.getUsernameFromHeader(request.getHeader("Authorization"));
		
		contato.setUsuario(userRepository.findByUsername(username));
		
		Contato contatoSalvo = repository.save(contato);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(contato.getId()).toUri();
		respose.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(contatoSalvo);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Contato> updateContato(@Valid @RequestBody Contato contato, @PathVariable Long id,HttpServletRequest request) throws EmptyResultDataAccessException, UserNotAuthorizedException{
		return new ResponseEntity<Contato>(service.updateContato(id, contato,TokenUtil.getUsernameFromHeader(request.getHeader("Authorization"))),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void deleteContato(@PathVariable Long id,HttpServletRequest request) throws UserNotAuthorizedException{
		
		Contato contato = repository.findById(id);
		String username = TokenUtil.getUsernameFromHeader(request.getHeader("Authorization"));
		
		if(contato != null && !contato.getUsuario().getUsername().equals(username)) {
			throw new UserNotAuthorizedException();
		}
		
		repository.delete(id);
	}
	
}
