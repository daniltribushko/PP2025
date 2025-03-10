package ru.tdd.backend.model.dto.orgaisations;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.tdd.backend.model.dto.DictionaryDto;
import ru.tdd.backend.model.dto.DtoVersion;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganisationDto extends DtoVersion {
    private String title;
    private String description;
    private String url;
    private List<DictionaryDto> tags;

    public OrganisationDto(Long id,
                           LocalDateTime creationDate,
                           LocalDateTime updateDate,
                           String title,
                           String description,
                           String url,
                           List<DictionaryDto> tags) {
        super(id, creationDate, updateDate);
        this.title = title;
        this.description = description;
        this.url = url;
        this.tags = tags;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private String url;
        private List<DictionaryDto> tags;
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

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder tags(List<DictionaryDto> tags) {
            this.tags = tags;
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

        public OrganisationDto build() {
            return new OrganisationDto(
                    this.id,
                    this.creationDate,
                    this.updateDate,
                    this.title,
                    this.description,
                    this.url,
                    this.tags
            );
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<DictionaryDto> getTags() {
        return tags;
    }

    public void setTags(List<DictionaryDto> tags) {
        this.tags = tags;
    }
}
