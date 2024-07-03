package com.registro.usuarios.controlador.dto;

import java.math.BigDecimal;

public class TransferenciaDTO {

    private BigDecimal monto;
    private String destinatario;

  

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }
}
