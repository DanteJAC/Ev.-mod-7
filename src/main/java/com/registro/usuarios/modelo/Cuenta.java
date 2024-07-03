package com.registro.usuarios.modelo;

import java.math.BigDecimal;

import javax.persistence.*;

@Entity
@Table(name = "cuentas")
public class Cuenta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String numeroCuenta;

	@Column(nullable = false)
	private BigDecimal saldo;

	@ManyToOne // Cambiado a ManyToOne si un usuario puede tener múltiples cuentas
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;

	public Cuenta() {
		// Constructor por defecto requerido por JPA
	}

	 public void depositar(BigDecimal monto) {
	        this.saldo = this.saldo.add(monto);
	    }
		// Puedes considerar manejar el caso cuando monto es null aquí
	

	public void retirar(BigDecimal monto) {
	    if (monto != null) {
	        this.saldo = this.saldo.subtract(monto); // Restar el monto del saldo
	    }
	    
	}

	 public void debitar(BigDecimal monto) {
	        this.saldo = this.saldo.subtract(monto);
	    }
	    
	
	

	// Getters y setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
