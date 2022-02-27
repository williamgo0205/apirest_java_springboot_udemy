package com.vendas.gestaovendas.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GestaoVendasExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String CONSTANT_VALIDATION_NOT_BLANK = "NotBlank";
    public static final String CONSTANT_VALIDATION_LENGTH = "Length";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<Error> errors = gerarListaDeErros(ex.getBindingResult());
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    private List<Error> gerarListaDeErros(BindingResult bindingResult) {
        List<Error> errors = new ArrayList<Error>();
        bindingResult.getFieldErrors().forEach(fildError -> {
            String msgUsuario = tartarMensagemDeErroParaUsuario(fildError);
            String msgDesenvolvedor = fildError.toString();
            errors.add(new Error(msgUsuario, msgDesenvolvedor));
        });
        return errors;
    }

    private String tartarMensagemDeErroParaUsuario(FieldError fildError) {
        if (fildError.getCode().equals(CONSTANT_VALIDATION_NOT_BLANK)) {
            return fildError.getDefaultMessage().concat(" é um campo obrigatório.");
        }
        if (fildError.getCode().equals(CONSTANT_VALIDATION_LENGTH)) {
            return fildError.getDefaultMessage().concat(String.format(" deve ter entre %s e %s caracteres.",
                    fildError.getArguments()[2], fildError.getArguments()[1]));
        }
        return fildError.toString();
    }
}
