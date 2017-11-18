package com.hardcode.secretfriend.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hardcode.secretfriend.model.Contato;
import com.hardcode.secretfriend.model.Usuario;

public interface ContatoRepository extends JpaRepository<Contato, Long>{

	public Contato findById(Long id);
	
	public List<Contato> findByUsuario(Usuario usuario);
	
}
