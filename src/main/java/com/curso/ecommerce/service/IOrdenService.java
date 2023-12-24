package com.curso.ecommerce.service;

import org.springframework.stereotype.Service;

import com.curso.ecommerce.model.Orden;

public interface IOrdenService {
	Orden save (Orden orden);
}
