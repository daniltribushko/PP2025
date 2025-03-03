package ru.tdd.backend.model.entities.organisations;

import jakarta.persistence.*;
import ru.tdd.backend.model.EntityVersion;

import java.util.List;

/** Сущность организации */
@Entity
public class Organisation extends EntityVersion {
    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "url")
    private String url;

    @OneToMany(mappedBy = "organisation", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Employee> employees;

    @OneToMany(mappedBy = "manageOrganisation", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private List<Employee> manageEmployees;

    @ManyToMany
    @JoinTable(
            name = "organisation_2_tag",
            joinColumns = @JoinColumn(name = "organisation"),
            inverseJoinColumns = @JoinColumn(name = "tag")
    )
    private List<OrganisationTag> tags;

    public Organisation() {
        super();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String title;
        private String description;
        private String url;
        private List<Employee> employees;
        private List<Employee> manageEmployees;
        private List<OrganisationTag> tags;

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

        public Builder employees(List<Employee> employees) {
            this.employees = employees;
            return this;
        }

        public Builder manageEmployees(List<Employee> manageEmployees) {
            this.manageEmployees = manageEmployees;
            return this;
        }

        public Builder tags(List<OrganisationTag> tags) {
            this.tags = tags;
            return this;
        }

        public Organisation build() {
            Organisation organisation = new Organisation();

            organisation.setTitle(title);
            organisation.setDescription(description);
            organisation.setUrl(url);
            organisation.setEmployees(employees);
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<OrganisationTag> getTags() {
        return tags;
    }

    public void setTags(List<OrganisationTag> tags) {
        this.tags = tags;
    }

    public List<Employee> getManageEmployees() {
        return manageEmployees;
    }

    public void setManageEmployees(List<Employee> manageEmployees) {
        this.manageEmployees = manageEmployees;
    }
}
