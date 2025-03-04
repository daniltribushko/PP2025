package ru.tdd.backend.controller.controllers.organisations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tdd.backend.controller.controllers.RestFullController;
import ru.tdd.backend.domen.annotations.SecuredAdminUser;
import ru.tdd.backend.domen.service.organisations.PostService;
import ru.tdd.backend.model.dto.DictionaryDto;
import ru.tdd.backend.model.dto.exceptions.ExceptionDTO;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/posts")
@SecurityRequirement(name = "jwtAuth")
@Tag(name = "Post Controller")
public class PostController implements RestFullController<DictionaryDto> {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "Create", description = "Создание должности")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Должность создана",
                            content = @Content(
                                    schema = @Schema(implementation = DictionaryDto.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пользователь не является администратором либо менеджером организации",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Должность с таким названием уже создана",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @Override
    @SecuredAdminUser
    public ResponseEntity<DictionaryDto> create(DictionaryDto dictionaryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(dictionaryDto));
    }

    @Operation(summary = "Update", description = "Обновление должности")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Должность обновлена",
                            content = @Content(
                                    schema = @Schema(implementation = DictionaryDto.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пользователь не является администратором либо менеджером организации",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Должность с идентификатором не найдена",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Новое имя должности занято",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
            }
    )
    @Override
    @SecuredAdminUser
    public ResponseEntity<DictionaryDto> update(DictionaryDto dictionaryDto) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.update(dictionaryDto));
    }

    @Operation(summary = "Delete by id", description = "Удаление пользователя")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Должность удалена"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пользователь не является администратором либо менеджером организации",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Должность с идентификатором не найдена",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @Override
    @SecuredAdminUser
    public ResponseEntity<?> delete(Long id) {
        postService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Get by id", description = "Получить должность по идентификатору")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Должность получена",
                            content = @Content(
                                    schema = @Schema(implementation = DictionaryDto.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Должность с идентификатором не найдена",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @Override
    @SecuredAdminUser
    public ResponseEntity<DictionaryDto> getById(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getById(id));
    }

    @Operation(summary = "Find All", description = "Получить несколько должностей")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Должности получены",
                            content = @Content(
                                    schema = @Schema(implementation = List.class)
                            )
                    )
            }
    )
    @Override
    @SecuredAdminUser
    public ResponseEntity<List<DictionaryDto>> findAll(String text, Integer page, Integer perPage) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.findAll(text, page, perPage));
    }
}
