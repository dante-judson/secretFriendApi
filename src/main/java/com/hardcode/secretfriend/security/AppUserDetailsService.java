package com.hardcode.secretfriend.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hardcode.secretfriend.model.Usuario;
import com.hardcode.secretfriend.repository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService{

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repository.findByUsername(username);
		if(usuario == null) {
			throw new UsernameNotFoundException("usuário não encontrado");
		} else {
			Set<SimpleGrantedAuthority> auth = new HashSet<>();
			auth.add(new SimpleGrantedAuthority("ADM"));
			return new User(usuario.getUsername(),usuario.getSenha(),auth );
		}
	}

}
