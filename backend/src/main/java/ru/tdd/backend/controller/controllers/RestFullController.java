package ru.tdd.backend.controller.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Интерфейс для контроллеров с базовыми операциями: создание, обновление, удаление, получение*/
public interface RestFullController<DTO> {
    @PostMapping
    ResponseEntity<DTO> create(DTO dto);
    @PutMapping
    ResponseEntity<DTO> update(DTO dto);
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id);
    @GetMapping("/{id}")
    ResponseEntity<DTO> getById(@PathVariable Long id);
    @GetMapping("/all")
    ResponseEntity<List<DTO>> findAll(
            @RequestParam(required = false)
            String text,
            @RequestParam(defaultValue = "0")
            Integer page,
            @RequestParam(defaultValue = "1")
            Integer perPage
    );
}
