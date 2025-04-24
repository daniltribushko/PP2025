package ru.tdd.backend.domen.service.vacancies.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tdd.backend.controller.repositories.organisations.OrganisationRepository;
import ru.tdd.backend.controller.repositories.users.UserRepository;
import ru.tdd.backend.controller.repositories.vacancies.DBFileRepository;
import ru.tdd.backend.controller.repositories.vacancies.SkillRepository;
import ru.tdd.backend.controller.repositories.vacancies.VacancyRepository;
import ru.tdd.backend.controller.repositories.vacancies.VacancyResponseRepository;
import ru.tdd.backend.domen.service.vacancies.VacancyService;
import ru.tdd.backend.model.dto.vacancies.VacancyDto;
import ru.tdd.backend.model.dto.vacancies.VacancyResponseDto;
import ru.tdd.backend.model.entities.DBFile;
import ru.tdd.backend.model.entities.organisations.Organisation;
import ru.tdd.backend.model.entities.users.User;
import ru.tdd.backend.model.entities.vacancies.*;
import ru.tdd.backend.model.exceptions.BaseException;
import ru.tdd.backend.model.exceptions.ValidationException;
import ru.tdd.backend.model.exceptions.organisations.OrganisationByIdNotFoundException;
import ru.tdd.backend.model.exceptions.users.UserByEmailNotFoundException;
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
    private final VacancyResponseRepository vacancyResponseRepository;
    private final OrganisationRepository organisationRepository;
    private final SkillRepository skillRepository;
    private final DBFileRepository dbFileRepository;
    private final UserRepository userRepository;

    @Autowired
    public VacancyServiceImp(
            VacancyRepository vacancyRepository,
            VacancyResponseRepository vacancyResponseRepository,
            OrganisationRepository organisationRepository,
            SkillRepository skillRepository,
            DBFileRepository dbFileRepository,
            UserRepository userRepository) {
        this.vacancyRepository = vacancyRepository;
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
                String[] array = file.getOriginalFilename().split("\\.");
                String fileName = "backend/src/main/resources/vacancies/" + vacancyRepository.getMaxId().orElse(1L) + "." + array[array.length - 1];
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
        vacancy.setOrganisation(null);
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
    public VacancyResponseDto addResponse(Long id, String username, VacancyResponseDto vacancyResponseDto, MultipartFile file) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new VacancyByIdNotFoundException(id));
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserByNameNotFoundException(username));

        VacancyResponse response = new VacancyResponse();
        response.setVacancy(vacancy);
        response.setAnswer(vacancyResponseDto.getAnswer());
        response.setAuthor(user);
        response.setResponseState(ResponseState.WAITING);

        if (!file.isEmpty()) {
            try (InputStream io = file.getInputStream()) {
                String[] array = file.getOriginalFilename().split("\\.");
                String fileName = "backend/src/main/resources/vacancies/responses/" + vacancy.getId() + "_" + user.getEmail() + "." + array[array.length - 1];
                Files.copy(io, Path.of(fileName), StandardCopyOption.REPLACE_EXISTING);
                response.setResume(new DBFile(fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new ValidationException("Резюме не должно быть пустым");
        }

        vacancy.getResponses().add(response);

        return vacancyResponseRepository.save(response).toDto();
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
        return vacancyRepository
                .findAll()
                .stream()
                .filter(v ->
                        (title == null || v.getTitle().contains(title)) &&
                                (skill == null || v.getSkills().stream().anyMatch(s -> s.getName().contains(skill))) &&
                                (type == null || Objects.equals(v.getType(), type)) &&
                                (orgName == null || v.getOrganisation().getTitle().contains(orgName))
                )
                .skip((long) page * perPage)
                .limit(perPage)
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

    @Override
    public VacancyResponseDto approve(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserByEmailNotFoundException(email));

        VacancyResponse response = vacancyResponseRepository.findById(id)
                .orElseThrow(() -> new VacancyResponseByIdNotFoundException(id));

        Vacancy vacancy = response.getVacancy();
        Organisation organisation = vacancy.getOrganisation();
        if (organisation.getManageEmployees()
                .stream()
                .noneMatch(e -> Objects.equals(e.getId(), user.getId()))
        ) {
            throw new BaseException("Пользователь не работник организации");
        }
        response.setResponseState(ResponseState.APPROVED);

        return vacancyResponseRepository.save(response).toDto();
    }

    @Override
    public VacancyResponseDto reject(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserByEmailNotFoundException(email));

        VacancyResponse response = vacancyResponseRepository.findById(id)
                .orElseThrow(() -> new VacancyResponseByIdNotFoundException(id));

        Vacancy vacancy = response.getVacancy();
        Organisation organisation = vacancy.getOrganisation();
        if (organisation.getManageEmployees()
                .stream()
                .noneMatch(e -> Objects.equals(e.getId(), user.getId()))
        ) {
            throw new BaseException("Пользователь не работник организации");
        }
        response.setResponseState(ResponseState.REJECTED);

        return vacancyResponseRepository.save(response).toDto();
    }
}
