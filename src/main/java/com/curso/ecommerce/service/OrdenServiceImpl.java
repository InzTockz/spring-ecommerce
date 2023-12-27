package com.curso.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.repository.IOrdenRepository;

@Service
public class OrdenServiceImpl implements IOrdenService {

	@Autowired
	private IOrdenRepository ordenRepository;
	
	@Override
	public Orden save(Orden orden) {
		// TODO Auto-generated method stub
		return ordenRepository.save(orden);
	}

	@Override
	public List<Orden> findAll() {
		return ordenRepository.findAll();
	}
	
	// 000010
	public String generarNumeroOrden() {
		int numero=0;
		String numeroContatenado="";
		
		List<Orden> ordenes = findAll();
		
		List<Integer> numeros = new ArrayList<Integer>();
		
		ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));
		
		if(ordenes.isEmpty()) {
			numero=1;
		} else {
			numero = numeros.stream().max(Integer::compare).get();
			numero++;
		}
		
		if(numero<10) {
			numeroContatenado = "000000000"+String.valueOf(numero);
		} else if (numero<100) {
			numeroContatenado = "00000000"+String.valueOf(numero);
		} else if(numero<1000) {
			numeroContatenado = "0000000"+String.valueOf(numero);
		} else if(numero<10000) {
			numeroContatenado = "000000"+String.valueOf(numero);
		}
		
		return numeroContatenado;
	}

	@Override
	public List<Orden> findByUsuario(Usuario usuario) {
		return ordenRepository.findByUsuario(usuario);
	}

}
