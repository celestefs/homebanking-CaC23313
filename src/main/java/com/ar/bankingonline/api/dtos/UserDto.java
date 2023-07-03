package com.ar.bankingonline.api.dtos;

import lombok.*;

@Data
public class UserDto {
    //trabajamos con los datos que traemos de la base de datos
    public UserDto(){}

    private Integer id;

    private String username;

    private String password;
}
