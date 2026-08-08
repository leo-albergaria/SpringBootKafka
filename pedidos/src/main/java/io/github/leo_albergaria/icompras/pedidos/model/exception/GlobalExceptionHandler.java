package io.github.leo_albergaria.icompras.pedidos.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(ValidationException ex) {
        Map<String, Object> body = Map.of(
                "campo", ex.getField(),
                "mensagem", ex.getMessage()
        );

        // Retorna HTTP 400 (Bad Request) de forma limpa, sem logar stack trace no servidor
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}