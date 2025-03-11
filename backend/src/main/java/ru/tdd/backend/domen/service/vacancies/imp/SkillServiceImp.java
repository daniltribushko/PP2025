package ru.tdd.backend.domen.service.vacancies.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.tdd.backend.controller.repositories.vacancies.SkillPagingRepository;
import ru.tdd.backend.controller.repositories.vacancies.SkillRepository;
import ru.tdd.backend.domen.annotations.DtoNameNotEmpty;
import ru.tdd.backend.domen.annotations.IsAdmin;
import ru.tdd.backend.domen.service.vacancies.SkillService;
import ru.tdd.backend.model.dto.DictionaryDto;
import ru.tdd.backend.model.entities.vacancies.Skill;
import ru.tdd.backend.model.exceptions.vacancies.SkillAlreadyExistException;
import ru.tdd.backend.model.exceptions.vacancies.SkillByIdNotFoundException;

import java.util.List;

@Service
public class SkillServiceImp implements SkillService {
    private SkillRepository skillRepository;
    private SkillPagingRepository skillPagingRepository;

    @Autowired
    public SkillServiceImp(
            SkillRepository skillRepository,
            SkillPagingRepository skillPagingRepository
    ) {
        this.skillRepository = skillRepository;
        this.skillPagingRepository = skillPagingRepository;
    }

    @IsAdmin
    @Override
    @DtoNameNotEmpty
    public DictionaryDto create(DictionaryDto dictionaryDto) {
        String name = dictionaryDto.getName();
        if (skillRepository.existsByName(name)) {
            throw new SkillAlreadyExistException(name);
        }
        Skill skill = new Skill();

        skill.setName(name);
        skill.setDescription(dictionaryDto.getDescription());

        return skillRepository.save(skill).toDto();
    }

    @Override
    public DictionaryDto getById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new SkillByIdNotFoundException(id))
                .toDto();
    }

    @IsAdmin
    @Override
    public DictionaryDto update(DictionaryDto dictionaryDto) {
        Long id = dictionaryDto.getId();
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new SkillByIdNotFoundException(id));
        String name = dictionaryDto.getName();
        if (name != null && !name.isEmpty()) {
            if (skillRepository.existsByName(name)) {
                throw new SkillAlreadyExistException(name);
            } else {
                skill.setName(name);
            }
        }

        String description = dictionaryDto.getDescription();
        if (description != null && !description.isEmpty()) {
            skill.setDescription(description);
        }

        return skillRepository.save(skill).toDto();
    }

    @IsAdmin
    @Override
    public void delete(Long id) {
        skillRepository.delete(skillRepository.findById(id).orElseThrow(() -> new SkillByIdNotFoundException(id)));
    }

    @Override
    public List<DictionaryDto> findAll(String text, Integer page, Integer perPage) {
        if (text != null && !text.isEmpty()) {
            return skillPagingRepository.findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                            text,
                            text,
                            PageRequest.of(page, perPage)
                    ).stream()
                    .map(Skill::toDto)
                    .toList();
        } else {
            return skillPagingRepository.findAll(PageRequest.of(page, perPage))
                    .stream()
                    .map(Skill::toDto)
                    .toList();
        }
    }
}
