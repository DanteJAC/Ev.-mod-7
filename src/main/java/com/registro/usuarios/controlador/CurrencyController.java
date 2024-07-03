package com.registro.usuarios.controlador;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.registro.usuarios.modelo.Usuario;
import com.registro.usuarios.servicio.UsuarioServicio;

@Controller
public class CurrencyController {

    @Autowired
    private UsuarioServicio usuarioServicio; // Asegúrate de tener un servicio UserService para obtener datos de usuario

    @GetMapping("/conversionFormulario")
    public String showConversionForm(Model model) {
        // Aquí podrías cargar los usuarios disponibles para la selección en el formulario
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "conversionForm";
    }

    @GetMapping("/conversion")
    public String convertCurrency(@RequestParam BigDecimal amount,
                                  @RequestParam String fromCurrency,
                                  @RequestParam String toCurrency,
                                  @RequestParam Long userId,
                                  Model model) {
        // Aquí deberías implementar la lógica de conversión y obtener el saldo del usuario por userId
        BigDecimal saldo = usuarioServicio.getSaldoByUserId(userId);

        // Aquí defines la lógica para separar los valores convertidos
        BigDecimal convertedValue = BigDecimal.ZERO; // Implementa la conversión real según fromCurrency y toCurrency
        
        // Aquí implementa la lógica de conversión real basada en fromCurrency y toCurrency
        if ("USD".equals(fromCurrency) && "CLP".equals(toCurrency)) {
            convertedValue = amount.multiply(BigDecimal.valueOf(800)); // Ejemplo: conversión de USD a CLP
        } else if ("USD".equals(fromCurrency) && "EUR".equals(toCurrency)) {
            convertedValue = amount.multiply(BigDecimal.valueOf(0.85)); // Ejemplo: conversión de USD a EUR
        }
        // Implementa más conversiones según sea necesario

        model.addAttribute("saldo", saldo);
        model.addAttribute("amount", amount);
        model.addAttribute("fromCurrency", fromCurrency);
        model.addAttribute("toCurrency", toCurrency);
        model.addAttribute("convertedValue", convertedValue);

        return "conversionResult";
    }
}
