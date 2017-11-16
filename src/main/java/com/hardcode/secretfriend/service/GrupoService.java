package com.hardcode.secretfriend.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.hardcode.secretfriend.model.Grupo;
import com.hardcode.secretfriend.repository.GrupoRepository;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository repository;

	public Grupo updateGrupo(Long id, Grupo grupo) throws EmptyResultDataAccessException{
		Grupo grupoSalvo = repository.findById(id);
		
		if(grupoSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		} else {
			BeanUtils.copyProperties(grupo, grupoSalvo, "id");
			return repository.save(grupoSalvo);
		}
	}
	
	
}
