package com.registro.usuarios.controlador;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.registro.usuarios.controlador.dto.TransferenciaDTO;
import com.registro.usuarios.modelo.Cuenta;
import com.registro.usuarios.modelo.Transaccion;
import com.registro.usuarios.modelo.Usuario;
import com.registro.usuarios.repositorio.CuentaRepository;
import com.registro.usuarios.servicio.TransaccionServicio;
import com.registro.usuarios.servicio.UsuarioServicio;

@Controller
public class TransferenciaController {

    @Autowired
    private UsuarioServicio usuarioService;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private TransaccionServicio transaccionServicio;

    @GetMapping("/transferencia")
    public String mostrarTransferencias(Model model) {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        model.addAttribute("cuentas", cuentas);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("transaccion", new TransferenciaDTO());
        return "transferencia";
    }

    @PostMapping("/transferencia")
    public String realizarTransferencia(@RequestParam("cuentaRemisora") String numeroCuentaRemisora,
                                        @RequestParam("monto") BigDecimal monto,
                                        @RequestParam("destinatario") String destinatarioEmail,
                                        Principal principal,
                                        Model model) {
        // Obtener el usuario actual (remisor)
        Usuario remisor = usuarioService.findByEmail(principal.getName());

        // Verificar si el remisor es nulo
        if (remisor == null) {
            model.addAttribute("error", "No se encontró el usuario remisor.");
            return "redirect:/transferencia";
        }

        // Obtener la cuenta del usuario remisor
        Cuenta cuentaRemisora = cuentaRepository.findByNumeroCuenta(numeroCuentaRemisora);
        if (cuentaRemisora == null) {
            model.addAttribute("error", "No se encontró la cuenta remisora.");
            return "redirect:/transferencia";
        }

        // Verificar si hay suficiente saldo para la transferencia
        BigDecimal saldoRemisor = cuentaRemisora.getSaldo();
        if (saldoRemisor.compareTo(monto) < 0) {
            model.addAttribute("error", "Saldo insuficiente para realizar la transferencia");
            return "redirect:/transferencia";
        }

        // Obtener el destinatario por su email
        Usuario destinatario = usuarioService.findByEmail(destinatarioEmail);
        if (destinatario == null) {
            model.addAttribute("error", "No se encontró el destinatario con el email proporcionado.");
            return "redirect:/transferencia";
        }

        // Obtener la cuenta del destinatario
        Cuenta cuentaDestinatario = destinatario.getCuentas().get(0); // Suponiendo una sola cuenta por usuario

        // Realizar la transferencia
        try {
            cuentaRemisora.debitar(monto);
            cuentaDestinatario.depositar(monto);
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo completar la transferencia.");
            return "redirect:/transferencia";
        }

        // Guardar los cambios en las cuentas en la base de datos
        cuentaRepository.save(cuentaRemisora);
        cuentaRepository.save(cuentaDestinatario);

        // Registrar la transacción en la base de datos
        Transaccion transaccion = new Transaccion();
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setCuentaOrigen(cuentaRemisora.getNumeroCuenta());
        transaccion.setCuentaDestino(cuentaDestinatario.getNumeroCuenta());
        transaccion.setMonto(monto);
        transaccionServicio.registrarTransaccion(transaccion);

        // Redirigir a la vista de transferencia con éxito
        return "redirect:/transferencia/exito";
    }

    @GetMapping("/transferencia/exito")
    public String transferenciaExitosa() {
        return "exito"; // Vista de éxito de la transferencia
    }

    @GetMapping("/movimientos")
    public String getMovimientosPage(Model model) {
        try {
            List<Transaccion> transacciones = transaccionServicio.obtenerTodasTransaccionesOrdenadas();
            model.addAttribute("transacciones", transacciones);
            return "movimientos";
        } catch (Exception e) {
            // Manejar el error o imprimirlo en los logs
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar los movimientos");
            return "error"; // Puedes crear una vista 'error.html' para mostrar mensajes de error
        }
    }
}
