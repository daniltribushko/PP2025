package ru.tdd.backend.model.dto.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.tdd.backend.model.dto.DtoVersion;
import ru.tdd.backend.model.dto.BaseDto;
import ru.tdd.backend.model.entities.users.UserState;

import java.time.LocalDateTime;

/** Dto пользователя */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto extends DtoVersion  {
    private Long id;
    private String email;
    private String lastName;
    private String middleName;
    private String firstName;
    private String password;
    private BaseDto role;
    private UserState userState;

    public UserDto(
            Long id,
            String email,
            String lastName,
            String middleName,
            String firstName,
            String password,
            BaseDto role,
            UserState userState,
            LocalDateTime creationDate,
            LocalDateTime updateDate
    ) {
        super(id, creationDate, updateDate);
        this.id = id;
        this.email = email;
        this.lastName = lastName;
        this.middleName = middleName;
        this.firstName = firstName;
        this.password = password;
        this.role = role;
        this.userState = userState;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String email;
        private String lastName;
        private String middleName;
        private String firstName;
        private String password;
        private BaseDto role;
        private UserState userState;
        private LocalDateTime creationDate;
        private LocalDateTime updateDate;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder middleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder role(BaseDto role) {
            this.role = role;
            return this;
        }

        public Builder userState(UserState userState) {
            this.userState = userState;
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

        public UserDto build() {
            return new UserDto(
                    this.id,
                    this.email,
                    this.lastName,
                    this.middleName,
                    this.firstName,
                    this.password,
                    this.role,
                    this.userState,
                    this.creationDate,
                    this.updateDate
            );
        }
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public BaseDto getRole() {
        return role;
    }

    public void setRole(BaseDto role) {
        this.role = role;
    }

    public UserState getUserState() {
        return userState;
    }

    public void setUserState(UserState userState) {
        this.userState = userState;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
