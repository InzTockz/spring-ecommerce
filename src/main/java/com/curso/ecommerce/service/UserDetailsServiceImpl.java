package com.curso.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.repository.IUsuarioRepository;

@Service
@EnableWebSecurity
public class UserDetailsServiceImpl implements UserDetailsService {

	
	@Autowired
	private IUsuarioRepository usuarioReposiroty;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioReposiroty.findByEmail(username);
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario no disponible");
		}
		return new MyUserDetails(usuario);
	}
	
	

}
