package com.github.akozubperez.math.arch.auth.dtos;

import java.util.List;
import lombok.Data;

@Data
public class UserDto {

    private String username;
    private String name;
    private String surname;
    private String email;
    private List<String> roles;
    private List<String> privileges;
}
