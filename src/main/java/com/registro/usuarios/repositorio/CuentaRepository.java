package com.registro.usuarios.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.registro.usuarios.modelo.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    // Método para obtener todas las cuentas usando el método proporcionado por JpaRepository
    List<Cuenta> findAll(); 

    Cuenta findByNumeroCuenta(String numeroCuenta);
  
}
