package ru.tdd.backend.controller.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.tdd.backend.controller.repositories.organisations.EmployeeRepository;
import ru.tdd.backend.controller.repositories.organisations.OrganisationRepository;
import ru.tdd.backend.model.entities.organisations.Employee;
import ru.tdd.backend.model.entities.organisations.Organisation;
import ru.tdd.backend.model.entities.users.User;
import ru.tdd.backend.model.exceptions.organisations.EmployeeDontOrganisationManagerException;

import java.util.Objects;

@Aspect
@Component
public class OrganisationAspect {
    private final EmployeeRepository employeeRepository;
    private final OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationAspect(
            EmployeeRepository employeeRepository,
            OrganisationRepository organisationRepository
    ) {
        this.employeeRepository = employeeRepository;
        this.organisationRepository = organisationRepository;
    }

    @Before(value =
            "@annotation(ru.tdd.backend.domen.annotations.EmployeeDontChangeAnotherOrganisation) && " +
                    "args(id, email)",
            argNames = "id, email")
    public void userDontChangeAnotherOrg(Long id, String email) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.isAdmin()) {
            Employee employee = employeeRepository.findByEmail(email).orElseThrow(
                    () -> new EmployeeDontOrganisationManagerException(email)
            );
            Organisation organisation = employee.getmanageOrganisation();
            if (organisation == null || !Objects.equals(organisation.getId(), id)) {
                throw new EmployeeDontOrganisationManagerException(email);
            }
        }
    }
}
