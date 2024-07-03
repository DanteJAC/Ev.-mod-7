package com.registro.usuarios.controlador.dto;

import java.math.BigDecimal;

public class TransferenciaRequest {
    private String cuentaOrigen;
    private String cuentaDestino;
    private BigDecimal monto;

    // Métodos getters y setters para cuentaOrigen, cuentaDestino y monto
    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}