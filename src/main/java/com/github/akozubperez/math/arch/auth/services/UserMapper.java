package com.github.akozubperez.math.arch.auth.services;

import com.github.akozubperez.math.arch.auth.dtos.UserDto;
import com.github.akozubperez.math.arch.auth.entities.User;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto map(User user, List<String> roles, List<String> privileges);
}
