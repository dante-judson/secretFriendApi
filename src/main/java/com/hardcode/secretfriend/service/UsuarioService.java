package com.hardcode.secretfriend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hardcode.secretfriend.dto.AvailableDTO;
import com.hardcode.secretfriend.model.Contato;
import com.hardcode.secretfriend.model.Usuario;
import com.hardcode.secretfriend.repository.ContatoRepository;
import com.hardcode.secretfriend.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private ContatoRepository contatoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public void addContatoDefault(Usuario usuario) {
		Contato c = new Contato();
		c.setEmail(usuario.getEmail());
		c.setNome(usuario.getNomeCompleto());
		c.setUsuario(usuario);
		
		contatoRepository.save(c);
	}
	
	public AvailableDTO emailAvailable(String email){
		Usuario u = usuarioRepository.findByEmail(email);
		if(u == null) {
			return new AvailableDTO(true, "Email disponível");
		} else {
			return new AvailableDTO(false, "Email indisponível");
		}
	}
	
	public AvailableDTO usernameAvailable(String username){
		Usuario u = usuarioRepository.findByUsername(username);
		if(u == null) {
			return new AvailableDTO(true, "Usuário disponível");
		} else {
			return new AvailableDTO(false, "Usuário indisponível");
		}
	}
	
}
