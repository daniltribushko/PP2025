package ru.tdd.backend.model.dto.vacancies;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import ru.tdd.backend.model.dto.DictionaryDto;
import ru.tdd.backend.model.dto.DtoVersion;
import ru.tdd.backend.model.dto.orgaisations.OrganisationDto;
import ru.tdd.backend.model.entities.DBFile;
import ru.tdd.backend.model.entities.vacancies.VacancyType;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VacancyDto extends DtoVersion {
    @Schema(
            name = "title",
            description = "Заголовок вакансии",
            type = "string",
            example = "Java Developer"
    )
    private String title;

    @Schema(
            name = "description",
            description = "Описание вакании",
            type = "string",
            example = "Java spring developer"
    )
    private String description;

    @Schema(
            name = "type",
            description = "Тип вакансии",
            example = "INTERNSHIP"
    )
    private VacancyType type;

    @Schema(
            name = "organisation",
            description = "Имя организации"
    )
    private Long organisation;
    private List<DictionaryDto> skills;
    private List<VacancyResponseDto> responses;
    private DBFile testTask;

    public VacancyDto() {
        super(null, null, null);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private VacancyType type;
        private Long organisation;
        private List<DictionaryDto> skills;
        private List<VacancyResponseDto> responses;
        private DBFile testTask;
        private LocalDateTime creationDate;
        private LocalDateTime updateDate;

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

        public Builder organisation(Long organisation) {
            this.organisation = organisation;
            return this;
        }

        public Builder skills(List<DictionaryDto> skills) {
            this.skills = skills;
            return this;
        }

        public Builder responses(List<VacancyResponseDto> responses) {
            this.responses = responses;
            return this;
        }

        public Builder testTask(DBFile testTask) {
            this.testTask = testTask;
            return this;
        }

        public Builder creationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder updateDate(LocalDateTime updateDate) {
            this.updateDate = updateDate;
            return this;
        }

        public VacancyDto build() {
            VacancyDto dto = new VacancyDto();
            dto.id = id;
            dto.title = title;
            dto.description = description;
            dto.type = type;
            dto.organisation = organisation;
            dto.skills = skills;
            dto.testTask = testTask;
            dto.responses = responses;
            dto.creationDate = creationDate;
            dto.updateDate = updateDate;
            return dto;
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

    public Long getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Long organisation) {
        this.organisation = organisation;
    }

    public List<DictionaryDto> getSkills() {
        return skills;
    }

    public void setSkills(List<DictionaryDto> skills) {
        this.skills = skills;
    }

    public DBFile getTestTask() {
        return testTask;
    }

    public void setTestTask(DBFile testTask) {
        this.testTask = testTask;
    }

    public List<VacancyResponseDto> getResponses() {
        return responses;
    }

    public void setResponses(List<VacancyResponseDto> responses) {
        this.responses = responses;
    }
}
