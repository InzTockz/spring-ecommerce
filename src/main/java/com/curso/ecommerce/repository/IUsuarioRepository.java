package com.curso.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.ecommerce.model.Usuario;
import java.util.List;


@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
	//Optional<Usuario> findByEmail(String email);
	Usuario findByEmail(String email); 
}
