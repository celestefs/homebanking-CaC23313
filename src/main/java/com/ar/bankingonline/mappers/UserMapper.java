package com.ar.bankingonline.mappers;

import com.ar.bankingonline.models.User;
import com.ar.bankingonline.models.dtos.UserDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    //los mappers me permiten enviar lso datos desde una entidad hacia DTO o viceversa

    public UserDto UserToDtoMap(User user){
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setId(user.getId());

        return dto;
    }


    public User dtoMapToUser(UserDto dto){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setId(dto.getId());

        return user;
    }
}
