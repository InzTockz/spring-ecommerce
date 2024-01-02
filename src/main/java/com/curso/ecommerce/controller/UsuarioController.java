package com.curso.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Roles;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.repository.IRolesRepository;
import com.curso.ecommerce.service.IOrdenService;
import com.curso.ecommerce.service.IUsuarioServices;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private IUsuarioServices usuarioService;
	
	@Autowired
	private IOrdenService ordenService;
	
	@Autowired
	private IRolesRepository rolesRepository;
	
	BCryptPasswordEncoder passEcndoe = new BCryptPasswordEncoder();
	
	// /usuario/registro
	@GetMapping("/registro")
	public String create() {
		return "usuario/registro";
	}
	
	@PostMapping("/save")
	public String save(Usuario usuario) {
		
		Optional<Roles> roles = rolesRepository.findById(2);
		
		logger.info("Usuario registro: {}", usuario);
		usuario.getRoles().add(roles.get());
		usuario.setPassword(passEcndoe.encode(usuario.getPassword()));
		usuarioService.save(usuario);
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {	
		return "usuario/login";
	}
	
	@GetMapping("/acceder")
	public String acceder(Usuario usuario, HttpSession session) {
		logger.info("Accesos: {}", usuario);
		
		Usuario user = usuarioService.findByEmail(usuario.getEmail());
		//Optional<Roles> roles = rolesRepository.findById(1);
		//logger.info("Usuario de db: {}", user.get());
		
		if(user!=null) {
			session.setAttribute("idusuario", user.getId());
			
			boolean codrol = user.getRoles().stream().anyMatch(rol -> rol.getIdrol() == 1);
			if(user.getRoles().stream().anyMatch(rol -> rol.getIdrol() == 1)) {
				return "redirect:/administrador";
			}
			else {
				return "redirect:/";
			}
		}else {
			logger.info("Usuario no existe");
		}
		return "redirect:/";
	}
	
	@GetMapping("/compras")
	public String obtenerCompras(HttpSession session, Model model) {
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		List<Orden> ordenes = ordenService.findByUsuario(usuario);
		
		model.addAttribute("ordenes", ordenes);
		
		return "usuario/compras";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalleCompra(@PathVariable int id, HttpSession session, Model model) {
		logger.info("Id de la orden: {}", id);
		Optional<Orden> orden = ordenService.findById(id);
		
		model.addAttribute("detalles", orden.get().getDetalle());
		//session
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "usuario/detallecompra";
	}
	
	@GetMapping("/cerrar")
	public String cerrarSesion(HttpSession session) {
		session.removeAttribute("idusuario");
		
		return "redirect:/";
	}
}
