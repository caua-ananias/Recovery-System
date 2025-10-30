package com.recovery.system.shared.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * Handler para erros de validação do @Valid (HTTP 400 Bad Request)
     * Formata a resposta para um JSON limpo (ex: {"name": "O nome é obrigatório"})
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.warn("Erro de validação de DTO: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            if (error instanceof FieldError) {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            } else {
                // Se for um erro global (ObjectError), usamos o nome do objeto
                String objectName = error.getObjectName();
                String errorMessage = error.getDefaultMessage();
                errors.put(objectName, errorMessage);
            }
        });
        return errors;
    }

    /**
     * Handler para nossa exceção customizada de duplicidade (HTTP 409 Conflict)
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleResourceAlreadyExists(ResourceAlreadyExistsException ex) {
        log.warn("Conflito de recurso: {}", ex.getMessage());
        return Map.of("error", ex.getMessage());
    }

    /**
     * Handler "Pega-Tudo" para erros inesperados (HTTP 500 Internal Server Error)
     * Isso evita que stack traces vazem para o cliente.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleInternalServerError(Exception ex) {
        // IMPORTANTE: Logar o stack trace completo do erro para debug
        log.error("Erro interno inesperado no servidor", ex);

        return Map.of("error", "Ocorreu um erro interno inesperado. Tente novamente mais tarde.");
    }
}
