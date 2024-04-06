package com.radik.my.project.utils.dto;

import com.radik.my.project.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Set;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal count;
    private boolean active;
    private Set<Role> roles;
    private String rolesLine;

}
