package com.hardcode.secretfriend.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hardcode.secretfriend.model.Grupo;
import com.hardcode.secretfriend.model.Usuario;

public interface GrupoRepository extends JpaRepository<Grupo, Long>{

	public Grupo findById(Long id);
	
	public List<Grupo> findByUsuario(Usuario usuario);
}
