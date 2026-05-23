package br.com.fiap.petpath.validation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GerenciadorValidacoes {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> gerirValidacoes(
            MethodArgumentNotValidException ex) {

        Map<String, String> erros = new HashMap<>();

        try {
            ex.getBindingResult().getFieldErrors().forEach(erro ->
                    erros.put(erro.getField(), erro.getDefaultMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body(erros);
    }

}
