package ru.tdd.backend.controller.controllers.users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.tdd.backend.domen.service.users.UserService;
import ru.tdd.backend.model.dto.exceptions.ExceptionDTO;
import ru.tdd.backend.model.dto.users.UserDto;
import ru.tdd.backend.model.exceptions.UserAccessDeniedException;
import ru.tdd.backend.model.entities.users.User;

import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/users")
@Tag(name = "User Controller")
@SecurityRequirement(name = "jwtAuth")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Find by id", description = "Получение пользователя по идентификатора")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь найден",
                            content = @Content(
                                    schema = @Schema(implementation = UserDto.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь с указанным идентификатором не найден",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<UserDto> findById(
            @PathVariable
            @Min(value = 1, message = "Идентификатор пользователя должен быть положительным числом")
            Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getById(id));
    }

    @Operation(summary = "Delete by id", description = "Удаление пользователя с указанным идентификатором")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Пользователь удален"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пользователь без роли администатора пытается удалить другого пользователя",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<?> delete(
            @PathVariable
            @Min(value = 1, message = "Идентификатор пользователя должен быть положительным числом")
            Long id
    ) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(id, user.getId()) && !user.isAdmin()) {
            throw new UserAccessDeniedException(user.getEmail());
        }
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Update", description = "Обновление пользователя")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь обновлен",
                            content = @Content(
                                    schema = @Schema(implementation = UserDto.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пользователь без роли администатора пытается обновить другого пользователя",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь с указанным электронным адресом не найден",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @PutMapping("")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto userDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(userDto.getId(), user.getId())) {
            throw new UserAccessDeniedException(user.getEmail());
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userDto));
    }

    @Operation(summary = "Find All", description = "Поиск списка пользователей по параметрам")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователи найдены",
                            content = @Content(
                                    schema = @Schema(implementation = UserDto.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @GetMapping("/all")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<List<UserDto>> findAll(
            @RequestParam(required = false)
            String role,
            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "Номер страницы должен быть больше либо равен 0")
            Integer page,
            @RequestParam(defaultValue = "1")
            @Min(value = 1, message = "Количество записей на странице должно быть больше 1")
            Integer perPage
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(role, page, perPage));
    }
}
