package ru.tdd.backend.domen.service.organisations.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.tdd.backend.controller.repositories.organisations.EmployeeRepository;
import ru.tdd.backend.controller.repositories.organisations.OrganisationPagingRepository;
import ru.tdd.backend.controller.repositories.organisations.OrganisationRepository;
import ru.tdd.backend.controller.repositories.organisations.OrganisationTagRepository;
import ru.tdd.backend.controller.repositories.users.UserRepository;
import ru.tdd.backend.domen.annotations.DtoNameNotEmpty;
import ru.tdd.backend.domen.annotations.EmployeeDontChangeAnotherOrganisation;
import ru.tdd.backend.domen.annotations.IsAdmin;
import ru.tdd.backend.domen.service.organisations.OrganisationService;
import ru.tdd.backend.model.dto.orgaisations.EmployeeDto;
import ru.tdd.backend.model.dto.orgaisations.OrganisationDto;
import ru.tdd.backend.model.entities.organisations.Employee;
import ru.tdd.backend.model.entities.organisations.Organisation;
import ru.tdd.backend.model.entities.organisations.OrganisationTag;
import ru.tdd.backend.model.entities.users.User;
import ru.tdd.backend.model.entities.users.UserState;
import ru.tdd.backend.model.exceptions.ValidationException;
import ru.tdd.backend.model.exceptions.organisations.OrganisationAlreadyExistException;
import ru.tdd.backend.model.exceptions.organisations.OrganisationByIdNotFoundException;
import ru.tdd.backend.model.exceptions.organisations.OrganisationTagAlreadyExistException;
import ru.tdd.backend.model.exceptions.organisations.OrganisationTagByIdNotFoundException;
import ru.tdd.backend.model.exceptions.users.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OrganisationServiceImp implements OrganisationService {
    private final OrganisationRepository organisationRepository;
    private final OrganisationTagRepository organisationTagRepository;
    private final OrganisationPagingRepository organisationPagingRepository;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public OrganisationServiceImp(
            OrganisationRepository organisationRepository,
            OrganisationTagRepository organisationTagRepository,
            OrganisationPagingRepository organisationPagingRepository,
            UserRepository userRepository,
            EmployeeRepository employeeRepository
    ) {
        this.organisationRepository = organisationRepository;
        this.organisationTagRepository = organisationTagRepository;
        this.organisationPagingRepository = organisationPagingRepository;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @EmployeeDontChangeAnotherOrganisation
    public OrganisationDto addTag(Long organisationId, Long tagId, String email) {
        Organisation organisation = organisationRepository.findById(organisationId)
                .orElseThrow(() -> new OrganisationByIdNotFoundException(organisationId));
        OrganisationTag organisationTag = organisationTagRepository.findById(tagId)
                .orElseThrow(() -> new OrganisationTagByIdNotFoundException(tagId));
        organisation.getTags().add(organisationTag);
        return organisationRepository
                .save(organisation)
                .toDto();
    }

    @Override
    @EmployeeDontChangeAnotherOrganisation
    public OrganisationDto deleteTag(Long organisationId, Long tagId, String email) {
        Organisation organisation = organisationRepository.findById(organisationId)
                .orElseThrow(() -> new OrganisationByIdNotFoundException(organisationId));
        OrganisationTag organisationTag = organisationTagRepository.findById(tagId)
                .orElseThrow(() -> new OrganisationTagByIdNotFoundException(tagId));
        organisation.getTags().remove(organisationTag);

        return organisationRepository
                .save(organisation)
                .toDto();
    }

    @Override
    public List<OrganisationDto> findAll(String tag, String title, Integer page, Integer perPage) {
        return organisationPagingRepository.findAll(PageRequest.of(page, perPage))
                .stream()
                .filter(org ->
                        tag == null ||
                                tag.isEmpty() ||
                                !org.getTags()
                                        .stream()
                                        .filter(orgTag ->
                                                orgTag.getName().contains(tag))
                                        .toList()
                                        .isEmpty()
                )
                .filter(org -> title == null ||
                        title.isEmpty() ||
                        org.getTitle().contains(title)
                )
                .map(Organisation::toDto)
                .toList();
    }

    @IsAdmin
    @Override
    @DtoNameNotEmpty
    public OrganisationDto create(OrganisationDto organisationDto) {
        String title = organisationDto.getTitle();
        if (organisationRepository.existsByTitle(title)) {
            throw new OrganisationTagAlreadyExistException(title);
        }

        String url = organisationDto.getUrl();
        String urlPatter = "/(https?:\\/\\/)?(www\\.)?\\S+\\.\\S+/g";

        if (url != null) {
            Matcher matcher = Pattern.compile(urlPatter).matcher(url);
            if (!matcher.find()) {
                throw new ValidationException("Поле: url должно иметь формать URL");
            }
        }

        Organisation organisation = Organisation.builder()
                .title(title)
                .description(organisationDto.getDescription())
                .url(url)
                .build();

        return organisationRepository.save(organisation).toDto();
    }

    @Override
    public OrganisationDto getById(Long id) {
        return organisationRepository.findById(id)
                .orElseThrow(() -> new OrganisationByIdNotFoundException(id))
                .toDto();
    }

    @Override
    @EmployeeDontChangeAnotherOrganisation
    public OrganisationDto update(OrganisationDto organisationDto, String email) {

        Organisation organisation = organisationRepository.findById(organisationDto.getId())
                .orElseThrow(() -> new OrganisationByIdNotFoundException(organisationDto.getId()));

        String title = organisationDto.getTitle();

        if (title != null && !title.isEmpty()) {
            if (organisationRepository.existsByTitle(title)) {
                throw new OrganisationAlreadyExistException(title);
            }
            organisation.setTitle(title);
        }

        String url = organisationDto.getUrl();
        //TODO исправит выражениеп
        String urlPatter = "/(https?://)?(www\\.)?\\S+\\.\\S+/g";

        if (url != null && !url.isEmpty()) {
            Matcher matcher = Pattern.compile(urlPatter).matcher(url);
            if (!matcher.find()) {
                throw new ValidationException("Поле: url должно иметь формать URL");
            } else {
                organisation.setUrl(url);
            }
        }

        if (organisationDto.getDescription() != null && !organisationDto.getDescription().isEmpty()) {
            organisation.setDescription(organisationDto.getDescription());
        }

        return organisationRepository.save(organisation).toDto();
    }

    @IsAdmin
    @Override
    @EmployeeDontChangeAnotherOrganisation
    public void delete(Long id, String email) {
        organisationRepository.delete(
                organisationRepository
                        .findById(id)
                        .orElseThrow(() -> new OrganisationByIdNotFoundException(id))
        );
    }

    @Override
    public OrganisationDto addEmployee(Long id, Long userId, String email) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserByIdNotFoundException(userId));
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserByNameNotFoundException(email));
        Organisation organisation = organisationRepository.findById(id)
                .orElseThrow(() -> new OrganisationByIdNotFoundException(id));

        if (
                !currentUser.isAdmin() &&
                        organisation.getManageEmployees()
                                .stream()
                                .allMatch(e -> Objects.equals(e.getId(), currentUser.getId()))
        ) {
            throw new UserNotOrganisationEmployeeException(currentUser.getEmail());
        }

        Optional<Employee> employeeOpt = employeeRepository.findById(user.getId());

        if (employeeOpt.isEmpty()) {

            employeeRepository.createEmployee(userId, organisation.getId());

            return organisationRepository.save(organisation).toDto();
        } else if (employeeOpt.get().getmanageOrganisation() == null) {

            organisation.getManageEmployees().add(employeeOpt.get());

            return organisationRepository.save(organisation).toDto();

        } else {
            throw new EmployeeAlreadyEXistsException(user.getEmail());
        }
    }

    @Override
    public OrganisationDto removeEmployee(Long id, Long userId, String email) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserByIdNotFoundException(userId));
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserByNameNotFoundException(email));
        Organisation organisation = organisationRepository.findById(id)
                .orElseThrow(() -> new OrganisationByIdNotFoundException(id));

        if (
                !currentUser.isAdmin() &&
                        organisation.getManageEmployees()
                                .stream()
                                .allMatch(e -> Objects.equals(e.getId(), currentUser.getId()))
        ) {
            throw new UserNotOrganisationEmployeeException(currentUser.getEmail());
        }

        employeeRepository.findById(user.getId()).ifPresent(e -> organisation.getManageEmployees().remove(e));

        return organisationRepository.save(organisation).toDto();
    }

    @Override
    public EmployeeDto getEmployee(Long orgId, Long id) {
        var organisation = organisationRepository.findById(orgId)
                .orElseThrow(() -> new OrganisationByIdNotFoundException(orgId));
        var employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeByIdNotFoundException(id));
        if (!organisation.getManageEmployees().contains(employee)) {
            throw new EmployeeByIdNotFoundException(id);
        }
        return EmployeeDto.builder()
                .id(employee.getId())
                .email(employee.getEmail())
                .creationDate(employee.getCreationDate())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .middleName(employee.getMiddleName())
                .role(employee.getRole().toDto())
                .organisation(employee.getmanageOrganisation().toDto())
                .userState(employee.getUserState())
                .build();
    }
}
