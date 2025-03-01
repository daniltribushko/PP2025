package ru.tdd.backend.model.users;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.tdd.backend.model.EntityVersion;
import ru.tdd.backend.model.dto.DtoEntity;
import ru.tdd.backend.model.dto.users.UserDto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/** Сущность пользователя */
@Entity
@Table(name = "users")
public class User extends EntityVersion implements UserDetails, DtoEntity<UserDto> {
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String lastName;
    private String middleName;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String password;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserState userState;

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public UserDto toDto() {
        return UserDto.builder()
                .email(email)
                .lastName(lastName)
                .middleName(middleName)
                .firstName(firstName)
                .id(id)
                .creationDate(creationDate)
                .updateDate(updateDate)
                .role(role.toDto())
                .userState(userState)
                .build();
    }

    public static class Builder {
        private String email;
        private String lastName;
        private String middleName;
        private String firstName;
        private String password;
        private Role role;
        private UserState state;

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

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Builder state(UserState state) {
            this.state = state;
            return this;
        }

        public User build() {
            User user = new User();
            user.email = this.email;
            user.lastName = this.lastName;
            user.middleName = this.middleName;
            user.firstName = this.firstName;
            user.password = this.password;
            user.role = this.role;
            user.userState = this.state;
            return user;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
