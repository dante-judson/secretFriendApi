package com.hardcode.secretfriend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hardcode.secretfriend.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	public Usuario findByUsername(String username);
	
	public Usuario findByEmail(String email);
	
}
