package ru.backend.myservice.mapper;

import ru.backend.myservice.dto.RoleDto;
import ru.backend.myservice.entity.RoleEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RoleMapper {
    RoleDto toDto(RoleEntity entity);

    RoleEntity toEntity(RoleDto dto);
}
