package com.hardcode.secretfriend.controller;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hardcode.secretfriend.model.Usuario;
import com.hardcode.secretfriend.repository.UsuarioRepository;

@RestController
@RequestMapping("/user")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;
	
	@PostMapping("/register")
	public ResponseEntity<Usuario> addUser(@Valid @RequestBody Usuario usuario,HttpServletResponse response){
		Usuario usuarioSalvo = repository.save(usuario);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(usuarioSalvo.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
	}
	
}
