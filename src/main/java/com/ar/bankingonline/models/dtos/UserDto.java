package com.ar.bankingonline.models.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDto {
    //trabajamos con los datos que traemos de la base de datos

    private Integer id;

    private String username;

    private String password;
}
