package com.curso.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso.ecommerce.model.Roles;

public interface IRolesRepository extends JpaRepository<Roles, Integer> {

}
