package ru.tdd.backend.domen.service.organisations.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tdd.backend.controller.repositories.organisations.OrganisationTagPagingRepository;
import ru.tdd.backend.controller.repositories.organisations.OrganisationTagRepository;
import ru.tdd.backend.controller.repositories.users.UserRepository;
import ru.tdd.backend.domen.annotations.DtoNameNotEmpty;
import ru.tdd.backend.domen.annotations.IsAdmin;
import ru.tdd.backend.domen.service.organisations.OrganisationTagService;
import ru.tdd.backend.model.dto.DictionaryDto;
import ru.tdd.backend.model.entities.organisations.OrganisationTag;
import ru.tdd.backend.model.exceptions.organisations.OrganisationTagAlreadyExistException;
import ru.tdd.backend.model.exceptions.organisations.OrganisationTagByIdNotFoundException;

import java.util.List;

@Service
public class OrganisationTagServiceImp implements OrganisationTagService {
    private final OrganisationTagRepository organisationTagRepository;
    private final OrganisationTagPagingRepository organisationTagPagingRepository;

    @Autowired
    public OrganisationTagServiceImp(OrganisationTagRepository organisationTagRepository, OrganisationTagPagingRepository organisationTagPagingRepository, UserRepository userRepository) {
        this.organisationTagRepository = organisationTagRepository;
        this.organisationTagPagingRepository = organisationTagPagingRepository;
    }

    @IsAdmin
    @Override
    @Transactional
    @DtoNameNotEmpty
    public DictionaryDto create(DictionaryDto dictionaryDto) {
        String name = dictionaryDto.getName();
        if (organisationTagRepository.existsByName(name)) {
            throw new OrganisationTagAlreadyExistException(name);
        }

        OrganisationTag organisationTag = new OrganisationTag(name, dictionaryDto.getDescription());
        return organisationTagRepository.save(organisationTag).toDto();
    }

    @Override
    @Transactional
    public List<DictionaryDto> findAll(String text, Integer page, Integer perPage) {
        if (text != null) {
            return organisationTagPagingRepository.findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderById(
                            text,
                            text,
                            PageRequest.of(page, perPage))
                    .stream()
                    .map(OrganisationTag::toDto)
                    .toList();
        } else {
            return organisationTagPagingRepository.findAllByOrderById(PageRequest.of(page, perPage))
                    .stream()
                    .map(OrganisationTag::toDto)
                    .toList();
        }
    }

    @Override
    public DictionaryDto getById(Long id) {
        return organisationTagRepository.findById(id).
                orElseThrow(() -> new OrganisationTagByIdNotFoundException(id))
                .toDto();
    }

    @IsAdmin
    @Override
    @Transactional
    public DictionaryDto update(DictionaryDto dictionaryDto) {
        String name = dictionaryDto.getName();
        Long id = dictionaryDto.getId();
        OrganisationTag organisationTag = organisationTagRepository.findById(id).
                orElseThrow(() -> new OrganisationTagByIdNotFoundException(id));

        if (name != null && !name.isEmpty()) {
            if (organisationTagRepository.existsByName(name)) {
                throw new OrganisationTagAlreadyExistException(name);
            } else {
                organisationTag.setName(name);
            }
        }

        organisationTag.setDescription(dictionaryDto.getDescription());

        return organisationTagRepository.save(organisationTag).toDto();
    }

    @IsAdmin
    @Override
    @Transactional
    public void delete(Long id) {
        organisationTagRepository.delete(organisationTagRepository.findById(id)
                .orElseThrow(() -> new OrganisationTagByIdNotFoundException(id)));
    }
}
