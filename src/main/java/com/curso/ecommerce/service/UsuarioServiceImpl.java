package com.curso.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.repository.IUsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioServices {

	
	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Override
	public Optional<Usuario> findById(int id) {
		return usuarioRepository.findById(id);
	}

	@Override
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	@Override
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario findByEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}
	

}
