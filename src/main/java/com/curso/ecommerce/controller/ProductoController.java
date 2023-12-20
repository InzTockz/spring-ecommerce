package com.curso.ecommerce.controller;

import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.ProductoService;

import jakarta.websocket.Decoder.Text;


@Controller
@RequestMapping("/productos")
public class ProductoController {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoService productoservice;

	@GetMapping("")
	public String show(Model model) {
		model.addAttribute("productos", productoservice.findAll());
		return "productos/show";
	}
	
	@GetMapping("/create")
	public String create() {
		return "productos/create";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		Producto producto = new Producto();
		Optional<Producto> optionalProducto=productoservice.get(id);
		producto = optionalProducto.get();
		
		 LOGGER.info("Producto buscado: {}", producto);
		model.addAttribute("producto", producto);
		 
		return "productos/edit";
	}
	
	@PostMapping("/save")
	public String save(Producto producto) {
		LOGGER.info("Este es el objeto de la vista {}", producto);
		Usuario u = new Usuario(1, "", "", "", "", "", "");
		producto.setUsuario(u);
		productoservice.save(producto);
		return "redirect:/productos";
	}
	
	@PostMapping("/update")
	public String udpdate(Producto producto) {
		productoservice.update(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		productoservice.delete(id);
		return "redirect:/productos";
	}
}
