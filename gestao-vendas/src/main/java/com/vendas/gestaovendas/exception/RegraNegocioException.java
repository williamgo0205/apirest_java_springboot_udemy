package com.vendas.gestaovendas.exception;

public class RegraNegocioException extends RuntimeException {

    private static final long serialVersionUID = 5406511561428515568L;

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
