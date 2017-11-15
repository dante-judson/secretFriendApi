package com.hardcode.secretfriend.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.hardcode.secretfriend.model.Contato;
import com.hardcode.secretfriend.repository.ContatoRepository;

@Service
public class ContatoService {

	@Autowired
	private ContatoRepository repository;
	
	public Contato updateContato(Long id, Contato contato) throws EmptyResultDataAccessException{
		Contato contatoSalvo = repository.findById(id);
		if(contato == null) {
			throw new EmptyResultDataAccessException(1);
		} else {
			BeanUtils.copyProperties(contato, contatoSalvo, "id");
			return repository.save(contatoSalvo);
		}
	}
}
