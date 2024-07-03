package com.registro.usuarios.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.registro.usuarios.controlador.TransferenciaController;
import com.registro.usuarios.controlador.dto.TransferenciaDTO;
import com.registro.usuarios.modelo.Cuenta;
import com.registro.usuarios.modelo.Transaccion;
import com.registro.usuarios.modelo.Usuario;
import com.registro.usuarios.repositorio.CuentaRepository;
import com.registro.usuarios.servicio.TransaccionServicio;
import com.registro.usuarios.servicio.UsuarioServicio;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransferenciaControllerTest {

    @InjectMocks
    TransferenciaController transferenciaController;

    @Mock
    UsuarioServicio usuarioServicio;

    @Mock
    CuentaRepository cuentaRepository;

    @Mock
    TransaccionServicio transaccionServicio;

    @Mock
    Model model;

    @Mock
    Principal principal;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMostrarTransferencias() {
        List<Cuenta> cuentas = new ArrayList<>();
        List<Usuario> usuarios = new ArrayList<>();

        when(cuentaRepository.findAll()).thenReturn(cuentas);
        when(usuarioServicio.listarUsuarios()).thenReturn(usuarios);

        String viewName = transferenciaController.mostrarTransferencias(model);

        assertEquals("transferencia", viewName);
        verify(model, times(1)).addAttribute("cuentas", cuentas);
        verify(model, times(1)).addAttribute("usuarios", usuarios);
        verify(model, times(1)).addAttribute("transaccion", new TransferenciaDTO());
    }

    @Test
    public void testRealizarTransferencia() {
        String numeroCuentaRemisora = "123456";
        BigDecimal monto = new BigDecimal("100.00");
        String destinatarioEmail = "destinatario@test.com";

        Usuario remisor = new Usuario();
        remisor.setEmail("remisor@test.com");
        Cuenta cuentaRemisora = new Cuenta();
        cuentaRemisora.setNumeroCuenta(numeroCuentaRemisora);
        cuentaRemisora.setSaldo(new BigDecimal("200.00"));

        Usuario destinatario = new Usuario();
        destinatario.setEmail(destinatarioEmail);
        Cuenta cuentaDestinatario = new Cuenta();
        cuentaDestinatario.setNumeroCuenta("654321");

        when(usuarioServicio.findByEmail("remisor@test.com")).thenReturn(remisor);
        when(cuentaRepository.findByNumeroCuenta(numeroCuentaRemisora)).thenReturn(cuentaRemisora);
        when(usuarioServicio.findByEmail(destinatarioEmail)).thenReturn(destinatario);
        when(destinatario.getCuentas()).thenReturn(List.of(cuentaDestinatario));

        String viewName = transferenciaController.realizarTransferencia(numeroCuentaRemisora, monto, destinatarioEmail, principal, model);

        assertEquals("redirect:/transferencia/exito", viewName);
        verify(cuentaRepository, times(1)).save(cuentaRemisora);
        verify(cuentaRepository, times(1)).save(cuentaDestinatario);

        Transaccion transaccion = new Transaccion();
        transaccion.setCuentaOrigen(numeroCuentaRemisora);
        transaccion.setCuentaDestino(cuentaDestinatario.getNumeroCuenta());
        transaccion.setMonto(monto);
        verify(transaccionServicio, times(1)).registrarTransaccion(transaccion);
    }

    @Test
    public void testGetMovimientosPage() {
        List<Transaccion> transacciones = new ArrayList<>();

        when(transaccionServicio.obtenerTodasTransaccionesOrdenadas()).thenReturn(transacciones);

        String viewName = transferenciaController.getMovimientosPage(model);

        assertEquals("movimientos", viewName);
        verify(model, times(1)).addAttribute("transacciones", transacciones);
    }
}
