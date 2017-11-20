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

import com.hardcode.secretfriend.dto.AvailableDTO;
import com.hardcode.secretfriend.model.Usuario;
import com.hardcode.secretfriend.repository.UsuarioRepository;
import com.hardcode.secretfriend.service.UsuarioService;
import com.hardcode.secretfriend.util.PasswordUtil;

@RestController
@RequestMapping("/user")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioService service;
	
	@PostMapping("/register")
	public ResponseEntity<Usuario> addUser(@Valid @RequestBody Usuario usuario,HttpServletResponse response){
		
		usuario.setSenha(PasswordUtil.encodePassword(usuario.getSenha()));
		
		Usuario usuarioSalvo = repository.save(usuario);
		
		service.addContatoDefault(usuarioSalvo);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(usuarioSalvo.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
	}
	
	@PostMapping("/email-available")
	public AvailableDTO emailAvailable(@RequestBody String email) {
		return service.emailAvailable(email);
	}
	
	@PostMapping("/username-available")
	public AvailableDTO usernameAvailable(@RequestBody String username) {
		return service.usernameAvailable(username);
	}
	
}
