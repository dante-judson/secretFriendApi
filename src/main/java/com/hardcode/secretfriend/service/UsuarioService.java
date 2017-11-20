package com.hardcode.secretfriend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hardcode.secretfriend.model.Contato;
import com.hardcode.secretfriend.model.Usuario;
import com.hardcode.secretfriend.repository.ContatoRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private ContatoRepository contatoRepository;

	public void addContatoDefault(Usuario usuario) {
		Contato c = new Contato();
		c.setEmail(usuario.getEmail());
		c.setNome(usuario.getNomeCompleto());
		c.setUsuario(usuario);
		
		contatoRepository.save(c);
	}
	
}
