package com.curso.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.curso.ecommerce.model.Orden;

public interface IOrdenService {
	List<Orden> findAll();
	Orden save (Orden orden);
}
