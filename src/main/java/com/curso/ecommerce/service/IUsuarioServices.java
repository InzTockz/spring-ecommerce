package com.curso.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.curso.ecommerce.model.Usuario;

public interface IUsuarioServices {
	Optional<Usuario> findById(int id);
	Usuario save (Usuario usuario);
	Optional<Usuario> findByEmail(String email);
	List<Usuario> findAll();
}
