package com.curso.ecommerce.controller;

import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.service.ProductoService;

@Controller
@RequestMapping("/")
public class HomeController {

	
	private final org.slf4j.Logger log= LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ProductoService productoservice;
	
	@GetMapping("")
	public String home(Model model) {
		
		model.addAttribute("productos", productoservice.findAll());
		
		return "usuario/home";
	}
	
	@GetMapping("productohome/{id}")
	public String productoHome(@PathVariable int id, Model model) {
		log.info("Id enviado como parametro {}", id);
		
		Producto p = new Producto();
		Optional<Producto> producto = productoservice.get(id);
		p = producto.get();
		
		model.addAttribute("producto", p);
		return "usuario/productohome";
	}
	
	@PostMapping("/cart")
	public String addcart() {
		return "usuario/carrito";
	}
	
}
