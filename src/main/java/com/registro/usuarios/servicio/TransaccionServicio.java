package com.registro.usuarios.servicio;

import com.registro.usuarios.modelo.Transaccion;
import com.registro.usuarios.repositorio.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransaccionServicio {

    @Autowired
    private TransaccionRepository transaccionRepository;

    public List<Transaccion> obtenerUltimas10Transacciones() {
        return transaccionRepository.findTop10ByOrderByFechaDesc();
    }
    
    public List<Transaccion> obtenerTodasTransaccionesOrdenadas() {
        return transaccionRepository.findAllByOrderByFechaDesc();
    }

    public void registrarTransaccion(Transaccion transaccion) {
        transaccionRepository.save(transaccion);
    }
}
