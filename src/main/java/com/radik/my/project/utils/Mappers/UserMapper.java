package com.radik.my.project.utils.Mappers;

import com.radik.my.project.entity.User;
import com.radik.my.project.utils.dto.UserDto;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getFirstName(), user.getLastName(), user.getEmail(), user.getCount());
    }

}
