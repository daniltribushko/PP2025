package ru.tdd.backend.controller.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tdd.backend.domen.service.users.AuthService;
import ru.tdd.backend.model.dto.exceptions.ExceptionDTO;
import ru.tdd.backend.model.dto.users.JwtToken;
import ru.tdd.backend.model.dto.users.SignIn;
import ru.tdd.backend.model.dto.users.SignUp;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@Tag(name = "Auth Controller")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Пользователь зарегестрирован и токен создан",
                            content = @Content(
                                    schema = @Schema(implementation = JwtToken.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Пользователь с введенным email уже создан",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пароль не подтвержден",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Данные не валидны",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @PostMapping("/sign-up")
    public ResponseEntity<JwtToken> signUp(@Valid @RequestBody SignUp signUp) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(signUp));
    }


    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь авторизован",
                            content = @Content(
                                    schema = @Schema(implementation = JwtToken.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Неверный email или пароль",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Данные не валидны",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь с указанным email не найден",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @PostMapping("/sign-in")
    public ResponseEntity<JwtToken> signIn(@Valid @RequestBody SignIn signIn) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.signIn(signIn));
    }
}
