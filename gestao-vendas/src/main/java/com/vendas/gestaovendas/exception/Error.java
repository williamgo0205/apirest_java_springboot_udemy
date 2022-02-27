package com.vendas.gestaovendas.exception;

public class Error {

    private String msgUsusario;
    private String msgDesenvolvedor;

    public Error(String msgUsusario, String msgDesenvolvedor) {
        this.msgUsusario = msgUsusario;
        this.msgDesenvolvedor = msgDesenvolvedor;
    }

    public String getMsgUsusario() {
        return msgUsusario;
    }

    public String getMsgDesenvolvedor() {
        return msgDesenvolvedor;
    }
}
