package ru.tdd.backend.domen.service.vacancies.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tdd.backend.controller.repositories.organisations.OrganisationRepository;
import ru.tdd.backend.controller.repositories.users.UserRepository;
import ru.tdd.backend.controller.repositories.vacancies.*;
import ru.tdd.backend.domen.service.vacancies.VacancyService;
import ru.tdd.backend.model.dto.vacancies.VacancyDto;
import ru.tdd.backend.model.dto.vacancies.VacancyResponseDto;
import ru.tdd.backend.model.entities.DBFile;
import ru.tdd.backend.model.entities.users.User;
import ru.tdd.backend.model.entities.vacancies.Skill;
import ru.tdd.backend.model.entities.vacancies.Vacancy;
import ru.tdd.backend.model.entities.vacancies.VacancyResponse;
import ru.tdd.backend.model.entities.vacancies.VacancyType;
import ru.tdd.backend.model.exceptions.organisations.OrganisationByIdNotFoundException;
import ru.tdd.backend.model.exceptions.users.UserByNameNotFoundException;
import ru.tdd.backend.model.exceptions.vacancies.SkillByIdNotFoundException;
import ru.tdd.backend.model.exceptions.vacancies.VacancyByIdNotFoundException;
import ru.tdd.backend.model.exceptions.vacancies.VacancyResponseByIdNotFoundException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class VacancyServiceImp implements VacancyService {
    private final VacancyRepository vacancyRepository;
    private final VacancyPagingRepository vacancyPagingRepository;
    private final VacancyResponseRepository vacancyResponseRepository;
    private final OrganisationRepository organisationRepository;
    private final SkillRepository skillRepository;
    private final DBFileRepository dbFileRepository;
    private final UserRepository userRepository;

    @Autowired
    public VacancyServiceImp(
            VacancyRepository vacancyRepository,
            VacancyPagingRepository vacancyPagingRepository,
            VacancyResponseRepository vacancyResponseRepository,
            OrganisationRepository organisationRepository,
            SkillRepository skillRepository,
            DBFileRepository dbFileRepository,
            UserRepository userRepository) {
        this.vacancyRepository = vacancyRepository;
        this.vacancyPagingRepository = vacancyPagingRepository;
        this.vacancyResponseRepository = vacancyResponseRepository;
        this.organisationRepository = organisationRepository;
        this.skillRepository = skillRepository;
        this.dbFileRepository = dbFileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public VacancyDto create(MultipartFile file, VacancyDto vacancyDto, String email) {
        Vacancy vacancy = Vacancy.builder()
                .title(vacancyDto.getTitle())
                .description(vacancyDto.getDescription())
                .organisation(
                        organisationRepository.findById(vacancyDto.getOrganisation())
                                .orElseThrow(() ->
                                        new OrganisationByIdNotFoundException(
                                                vacancyDto.getOrganisation()
                                        )
                                )
                )
                .skills(new HashSet<>())
                .responses(new HashSet<>())
                .type(vacancyDto.getType())
                .build();

        if (!file.isEmpty()) {
            try (InputStream io = file.getInputStream()) {
                String fileName = "backend/src/main/resources/vacancies/" + vacancy.getTitle() + " " + file.getOriginalFilename();
                Files.copy(io, Path.of(fileName), StandardCopyOption.REPLACE_EXISTING);
                vacancy.setTestTask(new DBFile(fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return vacancyRepository.save(vacancy).toDto();
    }

    @Override
    public VacancyDto update(VacancyDto vacancyDto, String email) {
        return null;
    }

    @Override
    public VacancyDto getById(Long id) {
        return vacancyRepository.findById(id)
                .orElseThrow(() -> new VacancyByIdNotFoundException(id))
                .toDto();
    }

    @Override
    public void delete(Long id, String email) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new VacancyByIdNotFoundException(id));
        DBFile testTask = vacancy.getTestTask();
        File file = testTask.getFile();
        if (file.exists()) {
            file.delete();
        }
        dbFileRepository.delete(testTask);

        vacancyRepository.delete(vacancy);
    }

    @Override
    public List<VacancyResponseDto> getResponses(Long id) {
        return vacancyRepository.findById(id)
                .orElseThrow(() -> new VacancyByIdNotFoundException(id))
                .getResponses()
                .stream()
                .map(VacancyResponse::toDto)
                .toList();
    }

    @Override
    public VacancyDto addResponse(Long id, String username, VacancyResponseDto vacancyResponseDto, MultipartFile file) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new VacancyByIdNotFoundException(id));
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserByNameNotFoundException(username));

        VacancyResponse response = new VacancyResponse();
        response.setVacancy(vacancy);
        response.setAnswer(vacancyResponseDto.getAnswer());
        response.setAuthor(user);

        if (!file.isEmpty()) {
            try (InputStream io = file.getInputStream()) {
                String fileName = "backend/src/main/resources/vacancies/responses/" + vacancy.getTitle() + " " + file.getOriginalFilename();
                Files.copy(io, Path.of(fileName), StandardCopyOption.REPLACE_EXISTING);
                response.setResume(new DBFile(fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        vacancy.getResponses().add(response);

        return vacancyRepository.save(vacancy).toDto();
    }

    @Override
    public VacancyResponseDto deleteResponse(Long id, Long responseId, String email) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new VacancyByIdNotFoundException(id));
        VacancyResponse response = vacancyResponseRepository.findById(responseId)
                .orElseThrow(() -> new VacancyResponseByIdNotFoundException(responseId));
        vacancy.getResponses().remove(response);

        vacancyRepository.save(vacancy);
        vacancyResponseRepository.delete(response);

        return response.toDto();
    }

    @Override
    public List<VacancyDto> getAll(
            String title,
            String skill,
            VacancyType type,
            String orgName,
            Integer page,
            Integer perPage
    ) {
        return vacancyPagingRepository
                .findAll(PageRequest.of(page, perPage))
                .stream()
                .filter(v ->
                        (title == null || v.getTitle().contains(title)) &&
                                (skill == null || v.getSkills().stream().anyMatch(s -> s.getName().contains(skill))) &&
                                (type == null || Objects.equals(v.getType(), type)) &&
                                (orgName == null || v.getOrganisation().getTitle().contains(orgName))
                )
                .map(Vacancy::toDto)
                .toList();
    }

    @Override
    public VacancyDto addSkill(Long id, Long skillId, String email) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new VacancyByIdNotFoundException(id));
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new SkillByIdNotFoundException(id));

        vacancy.getSkills().add(skill);

        return vacancyRepository.save(vacancy).toDto();
    }

    @Override
    public VacancyDto deleteSkill(Long id, Long skillId, String email) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new VacancyByIdNotFoundException(id));
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new SkillByIdNotFoundException(id));

        vacancy.getSkills().remove(skill);

        return vacancyRepository.save(vacancy).toDto();
    }
}
