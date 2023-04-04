package ru.backend.myservice.mapper;

import ru.backend.myservice.dto.UserDto;
import ru.backend.myservice.entity.UserEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    UserDto toDto(UserEntity entity);

    UserEntity toEntity(UserDto dto);
}
