package ru.backend.myservice.dto;


import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    @Singular
    private Set<RoleDto> roles = new HashSet<>();

}
