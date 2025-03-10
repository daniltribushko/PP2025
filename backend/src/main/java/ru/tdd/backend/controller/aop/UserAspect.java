package ru.tdd.backend.controller.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.tdd.backend.model.entities.users.User;
import ru.tdd.backend.model.exceptions.users.UserNotAdminException;

@Aspect
@Component
public class UserAspect {

    @Before(value = "@annotation(ru.tdd.backend.domen.annotations.IsAdmin)")
    public void isUserAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (!user.isAdmin()) {
            throw new UserNotAdminException(user.getEmail());
        }
    }
}
