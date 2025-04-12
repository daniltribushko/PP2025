package ru.tdd.backend.model.entities.organisations;

import jakarta.persistence.*;
import ru.tdd.backend.model.EntityVersion;
import ru.tdd.backend.model.dto.DtoEntity;
import ru.tdd.backend.model.dto.orgaisations.OrganisationDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/** Сущность организации */
@Entity
public class Organisation extends EntityVersion implements DtoEntity<OrganisationDto> {
    @Column(nullable = false, unique = true)
    private String title;

    @Column(length = 2500)
    private String description;

    @Column(name = "url")
    private String url;

    @OneToMany(mappedBy = "manageOrganisation", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Set<Employee> manageEmployees;

    @ManyToMany
    @JoinTable(
            name = "organisation_2_tag",
            joinColumns = @JoinColumn(name = "organisation"),
            inverseJoinColumns = @JoinColumn(name = "tag")
    )
    private Set<OrganisationTag> tags;

    public Organisation() {
        super();
        this.tags = new HashSet<>();
        this.manageEmployees = new HashSet<>();
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public OrganisationDto toDto() {
        return OrganisationDto
                .builder()
                .id(id)
                .title(title)
                .description(description)
                .url(url)
                .creationDate(creationDate)
                .updateDate(updateDate)
                .build();
    }

    public static class Builder {
        private String title;
        private String description;
        private String url;
        private Set<Employee> manageEmployees;
        private Set<OrganisationTag> tags;

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

        public Builder manageEmployees(Set<Employee> manageEmployees) {
            this.manageEmployees = manageEmployees;
            return this;
        }

        public Builder tags(Set<OrganisationTag> tags) {
            this.tags = tags;
            return this;
        }

        public Organisation build() {
            Organisation organisation = new Organisation();

            organisation.setTitle(title);
            organisation.setDescription(description);
            organisation.setUrl(url);
            organisation.setManageEmployees(manageEmployees);
            organisation.setTags(tags);

            return organisation;
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

    public Set<OrganisationTag> getTags() {
        return tags;
    }

    public void setTags(Set<OrganisationTag> tags) {
        this.tags = tags;
    }

    public Set<Employee> getManageEmployees() {
        return manageEmployees;
    }

    public void setManageEmployees(Set<Employee> manageEmployees) {
        this.manageEmployees = manageEmployees;
    }
}
