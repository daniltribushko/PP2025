package ru.tdd.backend.domen.service.vacancies;

import org.springframework.web.multipart.MultipartFile;
import ru.tdd.backend.model.dto.vacancies.VacancyDto;
import ru.tdd.backend.model.dto.vacancies.VacancyResponseDto;
import ru.tdd.backend.model.entities.vacancies.VacancyType;

import java.util.List;

public interface VacancyService {
    VacancyDto create(MultipartFile file, VacancyDto vacancyDto, String email);
    VacancyDto update(VacancyDto vacancyDto, String email);
    VacancyDto getById(Long id);
    void delete(Long id, String email);
    List<VacancyResponseDto> getResponses(Long id);
    VacancyResponseDto addResponse(
            Long id,
            String username,
            VacancyResponseDto vacancyResponseDto,
            MultipartFile file
    );

    VacancyResponseDto deleteResponse(
            Long id,
            Long responseId,
            String email
    );

    List<VacancyDto> getAll(
            String title,
            String skill,
            VacancyType type,
            String orgName,
            Integer page,
            Integer perPage
    );

    VacancyDto addSkill(Long id, Long skillId, String email);
    VacancyDto deleteSkill(Long id, Long skillId, String email);
}
