package ru.backend.myservice.service;

import ru.backend.myservice.dto.RoleDto;

public interface RoleService {

    RoleDto findByName(String name);

}
