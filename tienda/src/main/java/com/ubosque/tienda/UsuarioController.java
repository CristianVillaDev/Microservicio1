package com.ubosque.tienda;

import java.util.ArrayList;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubosque.DAO.UsuarioDAO;
import com.ubosque.DTO.Usuarios;

@RestController
@ComponentScan(basePackages = { "com.ubosque.DAO" })
@RequestMapping("/usuarios")
public class UsuarioController {

	@PostMapping("guardar")
	public boolean crear(@RequestBody Usuarios usuario) {
		boolean estado = false;
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		estado = (boolean) usuarioDAO.crear(usuario);
		return estado;
	}

	@RequestMapping("listar")
	public ArrayList<Usuarios> listar() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		return usuarioDAO.listarUsuarios();
	}

	@RequestMapping("listar/{cedula_usuario}")
	public ArrayList<Usuarios> listar(@PathVariable("cedula_usuario") int cedula_usuario) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		return usuarioDAO.listarUsuario(cedula_usuario);
	}

	@PutMapping("actualizar/{cedula_usuario}")
	public boolean actualizar(@PathVariable("cedula_usuario") int cedula_usuario, @RequestBody Usuarios usuario) {
		boolean estado = false;
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		estado = usuarioDAO.update(usuario, cedula_usuario);
		return estado;
	}

	@DeleteMapping("eliminar/{cedula_usuario}")
	public boolean delete(@PathVariable("cedula_usuario") int cedula_usuario) {
		boolean estado = false;
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		estado = usuarioDAO.delete(cedula_usuario);
		return estado;
	}

	@PostMapping("auth")
	public int autenticacion(@RequestBody Usuarios usuario) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		return usuarioDAO.login(usuario);
	}
}