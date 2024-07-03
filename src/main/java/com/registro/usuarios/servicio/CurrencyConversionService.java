package com.registro.usuarios.servicio;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyConversionService {

    // Simulación de tasas de cambio (valorar actualizar con fuente real)
    private static final Map<String, BigDecimal> exchangeRates = new HashMap<>();
    
    static {
        exchangeRates.put("USD", BigDecimal.valueOf(1.0));
        exchangeRates.put("EUR", BigDecimal.valueOf(0.85));
        exchangeRates.put("GBP", BigDecimal.valueOf(0.75));
    }

    public BigDecimal convertCurrency(BigDecimal amount, String fromCurrency, String toCurrency) {
        BigDecimal fromRate = exchangeRates.get(fromCurrency);
        BigDecimal toRate = exchangeRates.get(toCurrency);

        if (fromRate == null || toRate == null) {
            throw new IllegalArgumentException("Unsupported currency pair");
        }

        // Calcula la cantidad convertida
        BigDecimal convertedAmount = amount.multiply(fromRate).divide(toRate, 2, RoundingMode.HALF_UP);

        return convertedAmount;
    }
}
