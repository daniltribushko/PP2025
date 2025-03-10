package ru.tdd.backend.controller.controllers.organisations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tdd.backend.controller.controllers.RestFullController;
import ru.tdd.backend.domen.annotations.SecuredAdmin;
import ru.tdd.backend.domen.annotations.SecuredAdminUser;
import ru.tdd.backend.domen.service.organisations.OrganisationTagService;
import ru.tdd.backend.model.dto.DictionaryDto;
import ru.tdd.backend.model.dto.exceptions.ExceptionDTO;

import java.util.List;

@CrossOrigin
@RestController
@SecurityRequirement(name = "jwtAuth")
@RequestMapping("/organisations/tags")
@Tag(name = "Organisation Tag Controller")
public class OrganisationTagController implements RestFullController<DictionaryDto> {
    private final OrganisationTagService organisationTagService;

    @Autowired
    public OrganisationTagController(OrganisationTagService organisationTagService) {
        this.organisationTagService = organisationTagService;
    }

    @Operation(summary = "Create", description = "Создание тэга организации")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Тэг организации создан",
                            content = @Content(
                                    schema = @Schema(implementation = DictionaryDto.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пользователь не является администратором",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Тэг организации с указанным идентификатором уже создан",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @Override
    @SecuredAdmin
    public ResponseEntity<DictionaryDto> create(@Valid DictionaryDto dictionaryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organisationTagService.create(dictionaryDto));
    }

    @Operation(summary = "Update", description = "Обновление тега организации")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Тэг организации обновлен",
                            content = @Content(
                                    schema = @Schema(implementation = DictionaryDto.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пользователь не является администратором",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Тэг организации с указанным идентификатором не найден",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Тэг организации с указанным названием уже создан",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @Override
    @SecuredAdmin
    public ResponseEntity<DictionaryDto> update(@Valid DictionaryDto dictionaryDto) {
        return ResponseEntity.status(HttpStatus.OK).body(organisationTagService.update(dictionaryDto));
    }

    @Operation(summary = "Delete", description = "Удаление тэга организации")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Тэг организации удален"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пользователь не является администратором",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Тэг организации с указанным идентификатором не найден",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @Override
    @SecuredAdmin
    public ResponseEntity<?> delete(Long id) {
        organisationTagService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Find by id", description = "Получение тэга организации по идентификатору")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Тэг организации получен",
                            content = @Content(
                                    schema = @Schema(implementation = DictionaryDto.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Тэг организации не найден",
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
        return ResponseEntity.status(HttpStatus.OK).body(organisationTagService.getById(id));
    }

    @Operation(summary = "Find all", description = "Получение нескольких тэгов организации")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Тэги организаций найдены",
                            content = @Content(
                                    schema = @Schema(implementation = DictionaryDto.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @Override
    @SecuredAdminUser
    public ResponseEntity<List<DictionaryDto>> findAll(
            @RequestParam(required = false)
            String text,

            Integer page,
            Integer perPage
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(organisationTagService.findAll(text, page, perPage));
    }
}
