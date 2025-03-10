package ru.tdd.backend.controller.aop;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tdd.backend.model.dto.exceptions.ExceptionDTO;
import ru.tdd.backend.model.exceptions.ApiException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Hidden
@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ExceptionDTO> handleApiException(ApiException e) {
        return ResponseEntity.status(e.getCode())
                .body(
                        new ExceptionDTO(
                                e.getCode(),
                                e.getMessage(),
                                e.getTimestamp()
                        )
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .collect(Collectors.toMap(
                        err -> ((FieldError) err).getField(),
                        DefaultMessageSourceResolvable::getDefaultMessage)
                );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(
                        new ExceptionDTO(
                                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                                errors,
                                LocalDateTime.now()
                        )
                );
    }
}
