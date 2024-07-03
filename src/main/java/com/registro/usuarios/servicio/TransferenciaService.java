package com.registro.usuarios.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.registro.usuarios.modelo.Transaccion;

@Service
public class TransferenciaService {

    @Autowired
    private CrudRepository<Transaccion, Long> transaccionRepository; // Ajusta el tipo según el repositorio que estés usando

    @Transactional
    public void realizarTransferencia(Transaccion transferenciaForm) {
        // Lógica para realizar la transferencia
        Transaccion transaccion = new Transaccion();
        transaccion.setMonto(transferenciaForm.getMonto());
        transaccion.setCuentaDestino(transferenciaForm.getCuentaDestino());
        
        
        transaccionRepository.save(transaccion); // Aquí se usa la instancia no estática
        // Actualizar saldos u otras operaciones necesarias
    }
}
