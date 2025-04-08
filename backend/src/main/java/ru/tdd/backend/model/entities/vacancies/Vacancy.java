package ru.tdd.backend.model.entities.vacancies;

import jakarta.persistence.*;
import ru.tdd.backend.model.EntityVersion;
import ru.tdd.backend.model.dto.DtoEntity;
import ru.tdd.backend.model.dto.vacancies.VacancyDto;
import ru.tdd.backend.model.entities.DBFile;
import ru.tdd.backend.model.entities.organisations.Organisation;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Vacancy extends EntityVersion implements DtoEntity<VacancyDto> {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VacancyType type;

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "organisation")
    private Organisation organisation;

    @ManyToMany
    @JoinTable(
            name = "vacancies_2_skills",
            joinColumns = @JoinColumn(name = "vacancy"),
            inverseJoinColumns = @JoinColumn(name = "skill")
    )
    private Set<Skill> skills;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VacancyResponse> responses;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private DBFile testTask;

    public Vacancy() {
        super();
        skills = new HashSet<>();
        responses = new HashSet<>();
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public VacancyDto toDto() {
        return VacancyDto.builder()
                .id(id)
                .title(title)
                .description(description)
                .type(type)
                .creationDate(creationDate)
                .updateDate(creationDate)
                .responses(responses.stream().map(VacancyResponse::toDto).toList())
                .testTask(testTask)
                .organisation(organisation.getId())
                .skills(skills.stream().map(Skill::toDto).toList())
                .build();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private VacancyType type;
        private Organisation organisation;
        private Set<Skill> skills;
        private Set<VacancyResponse> responses;
        private DBFile testTask;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder type(VacancyType type) {
            this.type = type;
            return this;
        }

        public Builder organisation(Organisation organisation) {
            this.organisation = organisation;
            return this;
        }

        public Builder skills(Set<Skill> skills) {
            this.skills = skills;
            return this;
        }

        public Builder responses(Set<VacancyResponse> responses) {
            this.responses = responses;
            return this;
        }

        public Builder testTask(DBFile testTask) {
            this.testTask = testTask;
            return this;
        }

        public Vacancy build() {
            Vacancy vacancy = new Vacancy();

            vacancy.id = this.id;
            vacancy.title = this.title;
            vacancy.description = this.description;
            vacancy.type = this.type;
            vacancy.organisation = this.organisation;
            vacancy.skills = this.skills;
            vacancy.responses = this.responses;
            vacancy.testTask = this.testTask;

            return vacancy;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VacancyType getType() {
        return type;
    }

    public void setType(VacancyType type) {
        this.type = type;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public DBFile getTestTask() {
        return testTask;
    }

    public void setTestTask(DBFile testTask) {
        this.testTask = testTask;
    }

    public Set<VacancyResponse> getResponses() {
        return responses;
    }

    public void setResponses(Set<VacancyResponse> responses) {
        this.responses = responses;
    }
}
