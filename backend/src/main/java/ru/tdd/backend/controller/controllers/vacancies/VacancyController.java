package ru.tdd.backend.controller.controllers.vacancies;

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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tdd.backend.domen.annotations.SecuredAdminUser;
import ru.tdd.backend.domen.service.vacancies.VacancyService;
import ru.tdd.backend.model.dto.exceptions.ExceptionDTO;
import ru.tdd.backend.model.dto.vacancies.VacancyDto;
import ru.tdd.backend.model.dto.vacancies.VacancyResponseDto;
import ru.tdd.backend.model.entities.vacancies.VacancyType;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/vacancies")
@Tag(name = "Vacancy Controller")
@SecurityRequirement(name = "jwtAuth")
public class VacancyController {
    private final VacancyService vacancyService;

    @Autowired
    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @Operation(summary = "Create", description = "Создание заявки")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Вакансия создана",
                            content = @Content(
                                    schema = @Schema(implementation = VacancyDto.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пользователь не является администратором либо менеджером вакансии",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Ошибка валидации",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @SecuredAdminUser
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<VacancyDto> create(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @ModelAttribute @Valid VacancyDto vacancyDto,
            Principal principal
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vacancyService.create(file, vacancyDto, principal.getName()));
    }

    @Operation(summary = "Delete", description = "Удаление инспекции")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Вакансия удалена"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пользователь не является администратором, либо менеджером организации",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Вакансия не найдена",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @SecuredAdminUser
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        vacancyService.delete(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get responses", description = "Получение откликов на вакансию")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отклики получены",
                            content = @Content(
                                    schema = @Schema(implementation = VacancyResponseDto.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Вакансия с указанным идентификатором не найдена",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @SecuredAdminUser
    @GetMapping("/{id}/responses")
    public ResponseEntity<List<VacancyResponseDto>> getResponses(@PathVariable Long id) {
        return ResponseEntity.ok(vacancyService.getResponses(id));
    }

    @Operation(summary = "Add skill", description = "Добавить навык в вакансию")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Навык добавлен",
                            content = @Content(
                                    schema = @Schema(implementation = VacancyDto.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пользователь не является администратором либо менеджером компании",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь или вакансия или навык не найдены",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @SecuredAdminUser
    @PatchMapping("/{id}/skills/{skillId}/add")
    public ResponseEntity<VacancyDto> addSkill(
            @PathVariable Long id,
            @PathVariable Long skillId,
            Principal principal
    ) {
        return ResponseEntity.ok(vacancyService.addSkill(id, skillId, principal.getName()));
    }

    @Operation(summary = "Delete skill", description = "Удалить навык в вакансии")

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Навык удален",
                            content = @Content(
                                    schema = @Schema(implementation = VacancyDto.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пользователь не является администратором либо менеджером компании",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь или вакансия или навык не найдены",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @SecuredAdminUser
    @PatchMapping("/{id}/skills/{skillId}/remove")
    public ResponseEntity<VacancyDto> deleteSkill(
            @PathVariable Long id,
            @PathVariable Long skillId,
            Principal principal
    ) {
        return ResponseEntity.ok(vacancyService.deleteSkill(id, skillId, principal.getName()));
    }

    @Operation(summary = "Add response", description = "Откликнуться на вакансию")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отклик успешно создан",
                            content = @Content(
                                    schema = @Schema(implementation = VacancyDto.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь или вакансия не найдена",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
            }
    )
    @SecuredAdminUser
    @PatchMapping(value = "/{id}/responses/add",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<VacancyDto> addResponse(
            @PathVariable Long id,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @ModelAttribute @Valid VacancyResponseDto dto,
            Principal principal) {
        return ResponseEntity.ok(vacancyService.addResponse(id, principal.getName(), dto, file));
    }

    @Operation(summary = "Delete Response", description = "Удаление отклика")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отклик удален",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = VacancyResponseDto.class
                                    ),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь или вакансия или отклик не найдены",
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
    @PatchMapping("/{id}/responses/{responseId}/delete")
    public ResponseEntity<VacancyResponseDto> deleteResponse(
            @PathVariable Long id,
            @PathVariable Long responseId,
            Principal principal
    ) {
        return ResponseEntity.ok(vacancyService.deleteResponse(id, responseId, principal.getName()));
    }

    @Operation(summary = "Find all", description = " Поиск вакансий по параметрам")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Вакансии найдены"
                    )
            }
    )
    @SecuredAdminUser
    @GetMapping("/all")
    public ResponseEntity<List<VacancyDto>> findAll(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String skill,
            @RequestParam(required = false) VacancyType type,
            @RequestParam(required = false) String orgName,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "1") Integer perPage
    ) {
        return ResponseEntity.ok(vacancyService.getAll(title, skill, type, orgName, page, perPage));
    }
}
