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
import org.springframework.web.bind.annotation.*;
import ru.tdd.backend.domen.annotations.SecuredAdmin;
import ru.tdd.backend.domen.annotations.SecuredAdminUser;
import ru.tdd.backend.domen.service.organisations.OrganisationService;
import ru.tdd.backend.model.dto.exceptions.ExceptionDTO;
import ru.tdd.backend.model.dto.orgaisations.OrganisationDto;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/organisations")
@SecurityRequirement(name = "jwtAuth")
@Tag(name = "Organisation Controller")
public class OrganisationController {
    private final OrganisationService organisationService;

    @Autowired
    public OrganisationController(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    @Operation(summary = "Create", description = "Создание организации")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Организация создана",
                            content = @Content(
                                    schema = @Schema(implementation = OrganisationDto.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пользователь не является администратором",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ExceptionDTO.class
                                    ),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Организация с указанным идентификатором уже создана",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ExceptionDTO.class
                                    ),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Данне не валидны",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ExceptionDTO.class
                                    ),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @SecuredAdmin
    @PostMapping
    public ResponseEntity<OrganisationDto> create(@RequestBody OrganisationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organisationService.create(dto));
    }

    @Operation(summary = "Update", description = "Обновление организации")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Организация обновлена",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = OrganisationDto.class
                                    ),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пользователь не является администратором, либо мунеджером организации",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ExceptionDTO.class
                                    ),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Организация с указанным идентификатором не найдена",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ExceptionDTO.class
                                    ),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Организация с указанным заголовком уже создана",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ExceptionDTO.class
                                    ),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Данные не валидны"
                    )
            }
    )
    @PutMapping
    @SecuredAdminUser
    public ResponseEntity<OrganisationDto> update(@RequestBody OrganisationDto dto, Principal principal) {
        return ResponseEntity.status(HttpStatus.OK).body(organisationService.update(dto, principal.getName()));
    }

    @Operation(summary = "Add Tag", description = "Добавление тэга организации")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Тэг добавлен",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = OrganisationDto.class
                                    ),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пользователь не является администратором или менеджером органищации",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ExceptionDTO.class
                                    ),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Тэг или организация не найдена",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ExceptionDTO.class
                                    ),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Ошибка валидации",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ExceptionDTO.class
                                    ),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @SecuredAdminUser
    @PostMapping("/{id}/tags/{tagId}/add")
    public ResponseEntity<OrganisationDto> addTag(
            @PathVariable Long id,
            @PathVariable Long tagId,
            Principal principal
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        organisationService.addTag(
                                id,
                                tagId,
                                principal.getName()
                        )
                );
    }

    @Operation(summary = "Delete Tag", description = "Удаление тэга организации")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Тэг удален",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = OrganisationDto.class
                                    ),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пользователь не является администратором или менеджером органищации",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ExceptionDTO.class
                                    ),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Тэг или организация не найдена",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ExceptionDTO.class
                                    ),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Ошибка валидации",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ExceptionDTO.class
                                    ),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @SecuredAdminUser
    @PostMapping("/{id}/tags/{tagId}/remove")
    public ResponseEntity<OrganisationDto> deleteTag(
        @PathVariable Long id,
        @PathVariable Long tagId,
        Principal principal
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(organisationService.deleteTag(id, tagId, principal.getName()));
    }

    @Operation(summary = "Delete", description = "Удаление организации")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Организация удалена"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пользователь не является администратором или менеджером органищации",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ExceptionDTO.class
                                    ),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Организация с указанным идентификатором не найдена",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ExceptionDTO.class
                                    ),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @SecuredAdminUser
    @DeleteMapping("/{id}")
    public ResponseEntity<OrganisationDto> delete(@PathVariable Long id, Principal principal) {
        organisationService.delete(id, principal.getName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Find by Id", description = "Получение организации по id")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Организация найдена",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = OrganisationDto.class
                                    ),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Организация не найдена",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ExceptionDTO.class
                                    ),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    @SecuredAdminUser
    public ResponseEntity<OrganisationDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(organisationService.getById(id));
    }

    @Operation(summary = "Find All", description = "Получение несольких организаций")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Организации получены",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = OrganisationDto.class
                                    ),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @GetMapping("/all")
    @SecuredAdminUser
    public ResponseEntity<List<OrganisationDto>> findAll(
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String text,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "1") Integer perPage
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(organisationService.findAll(tag, text, page, perPage));
    }
}
