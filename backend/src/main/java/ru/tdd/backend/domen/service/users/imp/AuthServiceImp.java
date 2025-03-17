package ru.tdd.backend.domen.service.users.imp;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tdd.backend.controller.repositories.users.UserRepository;
import ru.tdd.backend.domen.service.BaseEntityService;
import ru.tdd.backend.domen.service.jwt.JwtTokenService;
import ru.tdd.backend.domen.service.users.AuthService;
import ru.tdd.backend.model.dto.users.UserDto;
import ru.tdd.backend.model.exceptions.BaseException;
import ru.tdd.backend.model.exceptions.users.UserByEmailAlreadyExistsException;
import ru.tdd.backend.model.exceptions.users.UserByEmailNotFoundException;
import ru.tdd.backend.model.exceptions.users.UserByNameNotFoundException;
import ru.tdd.backend.model.exceptions.users.WrongEmailPasswordException;
import ru.tdd.backend.model.entities.users.Role;
import ru.tdd.backend.model.entities.users.User;
import ru.tdd.backend.model.dto.users.JwtToken;
import ru.tdd.backend.model.dto.users.SignIn;
import ru.tdd.backend.model.dto.users.SignUp;
import ru.tdd.backend.model.entities.users.UserState;

import java.util.Objects;

@Service
public class AuthServiceImp implements AuthService {
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BaseEntityService<Role> roleService;

    @Autowired
    public AuthServiceImp(
            UserRepository userRepository,
            JwtTokenService jwtTokenService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            BaseEntityService<Role> roleService
    ) {
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.roleService = roleService;
    }

    @Override
    public JwtToken signUp(@Valid SignUp signUp) {
        String password = signUp.getPassword();
        String confirmPassword = signUp.getConfirmPassword();

        if (!Objects.equals(password, confirmPassword)) {
            throw new BaseException("Пароль не подтвержден");
        }

        String email = signUp.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new UserByEmailAlreadyExistsException(email);
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .lastName(signUp.getLastName())
                .firstName(signUp.getFirstName())
                .middleName(signUp.getMiddleName())
                .role(roleService.findByName("USER"))
                .state(UserState.ACTIVE)
                .build();

        userRepository.save(user);

        return new JwtToken(jwtTokenService.createToken(user));
    }

    @Override
    public JwtToken signIn(@Valid SignIn signIn) {
        String email = signIn.getEmail();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            signIn.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new WrongEmailPasswordException();
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserByEmailNotFoundException(email));

        return new JwtToken(jwtTokenService.createToken(user));
    }

    @Override
    public UserDto getFromToken(String token) {
        String email = jwtTokenService.parseToken(token).getSubject();
        return userRepository.findByEmail(email).orElseThrow(() -> new UserByNameNotFoundException(email)).toDto();
    }
}
