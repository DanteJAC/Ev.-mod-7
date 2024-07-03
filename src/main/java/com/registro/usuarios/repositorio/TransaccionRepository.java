package com.registro.usuarios.repositorio;

import com.registro.usuarios.modelo.Transaccion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionRepository extends CrudRepository<Transaccion, Long> {
    // Obtener las 10 transacciones más recientes ordenadas por fecha descendente
    List<Transaccion> findTop10ByOrderByFechaDesc();
    
    // Obtener todas las transacciones ordenadas por fecha descendente
    List<Transaccion> findAllByOrderByFechaDesc();
}
