package ru.tdd.backend.model.entities.organisations;

import jakarta.persistence.*;
import ru.tdd.backend.model.dto.users.UserDto;
import ru.tdd.backend.model.entities.users.Role;
import ru.tdd.backend.model.entities.users.User;
import ru.tdd.backend.model.entities.users.UserState;

import java.util.List;

@Entity
@Table(name = "employees")
public class Employee extends User {
    @ManyToOne
    @JoinColumn(name = "post")
    private Post post;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Contact> contacts;

    @ManyToOne
    @JoinColumn(name = "manage_organisation")
    private Organisation manageOrganisation;

    public static EmployeeBuilder employeeBuilder() {
        return new EmployeeBuilder();
    }

    public static class EmployeeBuilder {
        private String email;
        private String lastName;
        private String middleName;
        private String firstName;
        private String password;
        private Role role;
        private UserState state;
        private Post post;
        private List<Contact> contacts;
        private Organisation manageOrganisation;

        public EmployeeBuilder email(String email) {
            this.email = email;
            return this;
        }

        public EmployeeBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public EmployeeBuilder middleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public EmployeeBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public EmployeeBuilder password(String password) {
            this.password = password;
            return this;
        }

        public EmployeeBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public EmployeeBuilder state(UserState state) {
            this.state = state;
            return this;
        }

        public EmployeeBuilder post(Post post) {
            this.post = post;
            return this;
        }

        public EmployeeBuilder contacts(List<Contact> contacts) {
            this.contacts = contacts;
            return this;
        }

        public EmployeeBuilder manageOrganisation(Organisation manageOrganisation) {
            this.manageOrganisation = manageOrganisation;
            return this;
        }

        public Employee build() {
            Employee employee = new Employee();

            employee.setEmail(email);
            employee.setLastName(lastName);
            employee.setMiddleName(middleName);
            employee.setFirstName(firstName);
            employee.setPassword(password);
            employee.setRole(role);
            employee.setUserState(state);
            employee.post = post;
            employee.contacts = contacts;
            employee.manageOrganisation = manageOrganisation;

            return employee;
        }
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Organisation getmanageOrganisation() {
        return manageOrganisation;
    }

    public void setManageOrganisation(Organisation manageOrganisation) {
        this.manageOrganisation = manageOrganisation;
    }
}
