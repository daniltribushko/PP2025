package ru.tdd.backend.domen.service.users.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tdd.backend.controller.repositories.users.UserRepository;
import ru.tdd.backend.domen.service.users.UserService;
import ru.tdd.backend.model.dto.ObjectDto;
import ru.tdd.backend.model.dto.users.UserDto;
import ru.tdd.backend.model.exceptions.users.UserByEmailNotFoundException;
import ru.tdd.backend.model.exceptions.users.UserByIdNotFoundException;
import ru.tdd.backend.model.users.User;

import java.time.LocalDateTime;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserByIdNotFoundException(id))
                .toDto();
    }

    @Override
    @Transactional
    public UserDto update(UserDto userDto) {
        String email = userDto.getEmail();
        String firstName = userDto.getFirstName();
        String lastName = userDto.getLastName();
        String middleName = userDto.getMiddleName();
        String password = userDto.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserByEmailNotFoundException(email));

        if (email != null) {
            user.setEmail(email);
        }

        if (firstName != null) {
            user.setFirstName(firstName);
        }

        if (lastName != null) {
            user.setLastName(lastName);
        }

        if (middleName != null) {
            user.setMiddleName(middleName);
        }

        if (password != null) {
            user.setPassword(passwordEncoder.encode(password));
        }

        user.setUpdateDate(LocalDateTime.now());

        return userRepository.save(user)
                .toDto();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserByIdNotFoundException(id));
        userRepository.delete(user);
    }

    @Override
    public ObjectDto existsByEmail(String email) {
        return new ObjectDto(userRepository.existsByEmail(email));
    }
}
