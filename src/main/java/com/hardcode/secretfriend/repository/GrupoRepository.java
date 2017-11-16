package com.hardcode.secretfriend.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.hardcode.secretfriend.model.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long>{

	public Grupo findById(Long id);
}
