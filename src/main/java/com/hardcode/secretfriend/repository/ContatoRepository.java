package com.hardcode.secretfriend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hardcode.secretfriend.model.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long>{

	public Contato findById(Long id);
}
