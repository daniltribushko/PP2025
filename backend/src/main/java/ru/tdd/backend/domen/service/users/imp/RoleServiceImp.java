package ru.tdd.backend.domen.service.users.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tdd.backend.controller.repositories.RoleRepository;
import ru.tdd.backend.domen.service.BaseEntityService;
import ru.tdd.backend.model.exceptions.users.RoleByNameNotFoundException;
import ru.tdd.backend.model.users.Role;

@Service
public class RoleServiceImp implements BaseEntityService<Role> {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RoleByNameNotFoundException(name));
    }
}
