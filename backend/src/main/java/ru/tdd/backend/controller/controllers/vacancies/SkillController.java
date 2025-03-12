package ru.tdd.backend.controller.controllers.vacancies;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tdd.backend.controller.controllers.RestFullController;
import ru.tdd.backend.domen.annotations.SecuredAdmin;
import ru.tdd.backend.domen.service.vacancies.SkillService;
import ru.tdd.backend.model.dto.DictionaryDto;
import ru.tdd.backend.model.dto.exceptions.ExceptionDTO;

import java.util.List;

@CrossOrigin
@RestController
@SecurityRequirement(name = "jwtAuth")
@Tag(name = "Skill Controller")
@RequestMapping("/vacancies/skills")
public class SkillController implements RestFullController<DictionaryDto> {
    private final SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @Operation(summary = "Create", description = "Создание навыка")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Навык создан",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = DictionaryDto.class
                                    ),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пользователь не является админом",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ExceptionDTO.class
                                    ),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Навык с указанным именем уже создан",
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
    @Override
    @SecuredAdmin
    public ResponseEntity<DictionaryDto> create(@RequestBody DictionaryDto dictionaryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(skillService.create(dictionaryDto));
    }

    @Operation(summary = "Update", description = "Обновление навыка")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Навык обновлен",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = DictionaryDto.class
                                    ),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пользователь не является админом",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ExceptionDTO.class
                                    ),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Навык с указанным идентификатором не найден",
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
    @Override
    @SecuredAdmin
    public ResponseEntity<DictionaryDto> update(@RequestBody DictionaryDto dictionaryDto) {
        return ResponseEntity.status(HttpStatus.OK).body(skillService.update(dictionaryDto));
    }

    @Operation(summary = "Delete", description = "Удаление навыка")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Навык удален"
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
                            responseCode = "404",
                            description = "Навык не найден",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ExceptionDTO.class
                                    ),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @Override
    @SecuredAdmin
    public ResponseEntity<?> delete(Long id) {
        skillService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get by Id", description = "Получить навык по id")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Навык найден",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = DictionaryDto.class
                                    ),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Навык не найден",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ExceptionDTO.class
                                    ),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @Override
    public ResponseEntity<DictionaryDto> getById(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(skillService.getById(id));
    }

    @Operation(summary = "Find all", description = "Поиск нескольких навыков")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Навыки найдены",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = DictionaryDto.class
                                    ),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @Override
    public ResponseEntity<List<DictionaryDto>> findAll(String text, Integer page, Integer perPage) {
        return ResponseEntity.status(HttpStatus.OK).body(skillService.findAll(text, page, perPage));
    }
}
