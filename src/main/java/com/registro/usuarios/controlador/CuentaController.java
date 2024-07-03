package com.registro.usuarios.controlador;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.registro.usuarios.modelo.Cuenta;
import com.registro.usuarios.repositorio.CuentaRepository;

//Controlador para depósitos
@Controller
@RequestMapping("/transferir")
public class CuentaController {

 @Autowired
 private CuentaRepository cuentaRepository;

 @PostMapping()
 public ResponseEntity<String> transferir(@PathVariable Long id,
                                          @RequestParam Long cuentaDestinoId,
                                          @RequestParam Double monto) {
     // Obtener la cuenta de origen
     Optional<Cuenta> cuentaOrigenOptional = cuentaRepository.findById(id);
     if (cuentaOrigenOptional.isEmpty()) {
         return ResponseEntity.notFound().build();
     }
     Cuenta cuentaOrigen = cuentaOrigenOptional.get();

     // Obtener la cuenta de destino
     Optional<Cuenta> cuentaDestinoOptional = cuentaRepository.findById(cuentaDestinoId);
     if (cuentaDestinoOptional.isEmpty()) {
         return ResponseEntity.notFound().build();
     }
     Cuenta cuentaDestino = cuentaDestinoOptional.get();

     // Validar saldo suficiente en la cuenta de origen
     BigDecimal montoBigDecimal = BigDecimal.valueOf(monto);
     if (cuentaOrigen.getSaldo().compareTo(montoBigDecimal) < 0) {
         return ResponseEntity.badRequest().body("Saldo insuficiente en la cuenta de origen");
     }

     // Realizar la transferencia
     cuentaOrigen.setSaldo(cuentaOrigen.getSaldo().subtract(montoBigDecimal));
     cuentaDestino.setSaldo(cuentaDestino.getSaldo().add(montoBigDecimal));
     cuentaRepository.save(cuentaOrigen);
     cuentaRepository.save(cuentaDestino);

     return ResponseEntity.ok("Transferencia exitosa");
     
    
     
 }
 
}