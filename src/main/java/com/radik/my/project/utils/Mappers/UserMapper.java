package com.radik.my.project.utils.Mappers;

import com.radik.my.project.entity.User;
import com.radik.my.project.entity.enums.Role;
import com.radik.my.project.utils.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        StringBuilder rolesBuilder = new StringBuilder();
        for (Role role : user.getRoles()) {
            rolesBuilder.append(role.getValue());
            rolesBuilder.append(",");
        }
        rolesBuilder.deleteCharAt(rolesBuilder.length() - 1);

        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getCount(),
                user.isEnabled(), user.getRoles(), rolesBuilder.toString());
    }

    public static List<UserDto> toListUserDto(List<User> userList) {
        List<UserDto> result = new ArrayList<>();

        for (int i = 0; i < userList.size(); i++) {
            UserDto dto = UserMapper.toUserDto(userList.get(i));
            result.add(dto);
        }

        return result;
    }

}
