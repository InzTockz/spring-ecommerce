package com.curso.ecommerce.service;

import java.util.Optional;

import com.curso.ecommerce.model.Usuario;

public interface IUsuarioServices {
	Optional<Usuario> findById(int id);
	Usuario save (Usuario usuario);
	Optional<Usuario> findByEmail(String email);
}
