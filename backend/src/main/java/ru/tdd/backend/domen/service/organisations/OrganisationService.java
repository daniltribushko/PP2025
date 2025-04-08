package ru.tdd.backend.domen.service.organisations;

import ru.tdd.backend.model.dto.orgaisations.EmployeeDto;
import ru.tdd.backend.model.dto.orgaisations.OrganisationDto;

import java.util.List;

public interface OrganisationService {
    OrganisationDto create(OrganisationDto dto);
    OrganisationDto addTag(Long organisationId, Long tagId, String email);
    OrganisationDto deleteTag(Long organisationId, Long tagId, String email);
    List<OrganisationDto> findAll(String tag, String title, Integer page, Integer perPage);
    OrganisationDto getById(Long id);
    OrganisationDto update(OrganisationDto dto, String email);
    void delete(Long id, String email);

    OrganisationDto addEmployee(Long id, Long userId, String email);
    OrganisationDto removeEmployee(Long id, Long userId, String email);

    EmployeeDto getEmployee(Long orgId, Long id);
}
