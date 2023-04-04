package ru.backend.myservice.service.impl;

import ru.backend.myservice.dto.RoleDto;
import ru.backend.myservice.mapper.RoleMapper;
import ru.backend.myservice.repository.RoleRepository;
import ru.backend.myservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper mapper;

    @Override
    public RoleDto findByName(String name) {
        return mapper.toDto(roleRepository.findByName(name).orElseThrow()); // TODO: create custom exception
    }
}
