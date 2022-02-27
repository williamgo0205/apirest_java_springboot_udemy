package com.vendas.gestaovendas.exception;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class GestaoVendasExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String CONSTANT_VALIDATION_NOT_BLANK = "NotBlank";
    public static final String CONSTANT_VALIDATION_LENGTH = "Length";

    /**
     *  Classe de extensao ResponseEntityExceptionHandler consegue tratar as seguintes classes de erro
     *     HttpRequestMethodNotSupportedException .class,
     *     HttpMediaTypeNotSupportedException .class,
     *     HttpMediaTypeNotAcceptableException .class,
     *     MissingPathVariableException .class,
     *     MissingServletRequestParameterException .class,
     *     ServletRequestBindingException .class,
     *     ConversionNotSupportedException .class,
     *     TypeMismatchException .class,
     *     HttpMessageNotReadableException .class,
     *     HttpMessageNotWritableException .class,
     *     MethodArgumentNotValidException.class,
     *     MissingServletRequestPartException .class,
     *     BindException .class,
     *     NoHandlerFoundException .class,
     *     AsyncRequestTimeoutException .class
     *
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<Error> errors = gerarListaDeErros(ex.getBindingResult());
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Excecao EmptyResultDataAccessException não existente na extensao ResponseEntityExceptionHandler
     * por esse motivo foi criada com a anotacao  @ExceptionHandler(EmptyResultDataAccessException.class)
     * indicando a classe de excecao
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
                                                                        WebRequest request){
        String msgUsuario = "Recurso não encontrado.";
        String msgDesenvolvedor = ex.toString();
        List<Error> errors = Arrays.asList(new Error(msgUsuario, msgDesenvolvedor));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
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
