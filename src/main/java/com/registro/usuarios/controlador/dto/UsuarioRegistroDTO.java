package com.registro.usuarios.controlador.dto;

import java.util.List;

import com.registro.usuarios.modelo.Rol;

public class UsuarioRegistroDTO {

    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private List<Rol> roles; // Campo para roles

    
    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UsuarioRegistroDTO(String nombre, String apellido, String email, String password, List<Rol> roles) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	public UsuarioRegistroDTO() {
		super();
	}
    
    
}
