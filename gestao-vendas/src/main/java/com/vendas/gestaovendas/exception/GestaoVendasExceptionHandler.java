package com.vendas.gestaovendas.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
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
        log.error("Recurso não encontrado.");
        String msgUsuario = "Recurso não encontrado.";
        String msgDesenvolvedor = ex.toString();
        List<Error> errors = Arrays.asList(new Error(msgUsuario, msgDesenvolvedor));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * Excecao RegraNegocioException não existente na extensao ResponseEntityExceptionHandler
     * por esse motivo foi criada com a anotacao  @ExceptionHandler(RegraNegocioException.class)
     * indicando a classe de excecao, classe essa criada no projeto
     */
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Object> handleEmptyRegraNegocioException(RegraNegocioException ex,
                                                                       WebRequest request) {
        log.error("Erro de Regra de Negocio.");
        String msgUsuario = ex.getMessage();
        String msgDesenvolvedor = ex.getMessage();
        List<Error> errors = Arrays.asList(new Error(msgUsuario, msgDesenvolvedor));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Excecao DataIntegrityViolationException criada para tratamento de integridade de Banco de Dados
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                        WebRequest request) {
        log.error("Recurso não encontrado. Foreign Key não localizada.");
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
            log.error("Erro de validação: campo obrigatório não informado.");
            return fildError.getDefaultMessage().concat(" é um campo obrigatório.");
        }
        if (fildError.getCode().equals(CONSTANT_VALIDATION_LENGTH)) {
            log.error("Erro na validação do tamanho do campo.");
            return fildError.getDefaultMessage().concat(String.format(" deve ter entre %s e %s caracteres.",
                    fildError.getArguments()[2], fildError.getArguments()[1]));
        }
        return fildError.toString();
    }
}
