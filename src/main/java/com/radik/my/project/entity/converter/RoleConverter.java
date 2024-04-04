package com.radik.my.project.entity.converter;

import com.radik.my.project.entity.enums.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.*;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Set<Role>, String>
{
    @Override
    public String convertToDatabaseColumn(Set<Role> roles) {
        StringBuilder convert = new StringBuilder();
        for (Role role : roles) {
            convert.append(role.getValue());
            convert.append(",");
        }
        String result = convert.toString();
        return result.substring(0, result.length() - 1);
    }

    @Override
    public Set<Role> convertToEntityAttribute(String s) {
        Set<Role> roles = new HashSet<>();
        if (s.contains(",")) {
            List<String> strRoles = new ArrayList<>(Arrays.asList(s.split(",")));
            List<Role> enumRoles = new ArrayList<>(Arrays.asList(Role.values()));

            for (Role role : enumRoles) {
                if (strRoles.contains(role.getValue()))
                    roles.add(role);
            }
        } else {
            Role[] enumRoles = Role.values();
            for (Role role : enumRoles) {
                if (role.getValue().equals(s)) {
                    roles.add(role);
                    break;
                }
            }
        }
        return roles;
    }
}
