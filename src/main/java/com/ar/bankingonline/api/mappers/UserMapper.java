package com.ar.bankingonline.api.mappers;

import com.ar.bankingonline.api.dtos.UserDto;
import com.ar.bankingonline.domain.models.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    //los mappers me permiten enviar los datos desde una entidad hacia DTO o viceversa

    public User dtoToUser(UserDto dto){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;
    }

    public UserDto userMapToDto(User user){
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setId(user.getId());
        return dto;
    }
}