package com.curso.ecommerce.controller;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.ProductoService;
import com.curso.ecommerce.service.UploadFileService;


@Controller
@RequestMapping("/productos")
public class ProductoController {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoService productoservice;
	
	@Autowired
	private UploadFileService upload;

	@GetMapping("")
	public String show(Model model) {
		model.addAttribute("productos", productoservice.findAll());
		return "productos/show";
	}
	
	@GetMapping("/create")
	public String create() {
		return "productos/create";
	}
	
	@PostMapping("/save")
	public String save(Producto producto,@RequestParam("img") MultipartFile file) throws IOException {
		LOGGER.info("Este es el objeto de la vista {}", producto);
		Usuario u = new Usuario(1, "", "", "", "", "", "");
		producto.setUsuario(u);
		
		//imagen
		if(producto.getId()==null) { //cuando se crea un producto
			String nombreImagen = upload.saveImage(file);
			producto.setImagen(nombreImagen);
		} else {
			
		}
		
		productoservice.save(producto);
		return "redirect:/productos";
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
	
	@PostMapping("/update")
	public String udpdate(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
		
		if(file.isEmpty()) { //editamos el producto pero no cambiamos la imagen
			Producto p = new Producto();
			p = productoservice.get(producto.getId()).get();
			producto.setImagen(p.getImagen());
		} else { // cuando se edita también la imagen
			
			Producto p = new Producto();
			p = productoservice.get(producto.getId()).get();
			
			//eliminar cuando no sea la imagen por defecto
			if(p.getImagen().equals("default.jpg")) {
				upload.deleteImage(p.getNombre());
			}
			
			String nombreImagen = upload.saveImage(file);
			producto.setImagen(nombreImagen);
		}
		
		productoservice.update(producto);
		return "redirect:/productos";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		
		Producto p = new Producto();
		p = productoservice.get(id).get();
		
		//eliminar cuando no sea la imagen por defecto
		if(!p.getImagen().equals("default.jpg")) {
			upload.deleteImage(p.getImagen());
		}
		
		productoservice.delete(id);
		return "redirect:/productos";
	}
}
