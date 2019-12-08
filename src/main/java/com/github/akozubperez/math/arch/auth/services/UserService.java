package com.github.akozubperez.math.arch.auth.services;

import com.github.akozubperez.math.arch.auth.dtos.UserDto;
import com.github.akozubperez.math.arch.auth.entities.User;
import com.github.akozubperez.math.arch.auth.repositories.PrivilegeRepository;
import com.github.akozubperez.math.arch.auth.repositories.RoleRepository;
import com.github.akozubperez.math.arch.auth.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class UserService {

    private final PrivilegeRepository privilegeRepository;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<String> getRoles(@NonNull String username) {
        return roleRepository.findByUsername(username.toLowerCase());
    }

    @Transactional(readOnly = true)
    public List<String> getPrivileges(@NonNull String username) {
        return getPrivileges(username.toLowerCase(), getRoles(username));
    }

    private List<String> getPrivileges(String username, List<String> roles) {
        return Stream.of(privilegeRepository.findByRoles(roles), privilegeRepository.findByUsername(username))
                .flatMap(List::stream).distinct().collect(Collectors.toList());
    }

    public UserDto getUser(@NonNull String username) {
        return map(userRepository.getOne(username.toLowerCase()));
    }

    @Transactional(readOnly = true)
    public Optional<UserDto> findUser(@NonNull String username) {
        return userRepository.findById(username.toLowerCase()).map(this::map);
    }

    private UserDto map(User user) {
        UserDto result = null;
        if (user != null) {
            List<String> roles = getRoles(user.getUsername());
            List<String> privileges = getPrivileges(user.getUsername(), roles);
            result = userMapper.map(user, roles, privileges);
        }
        return result;
    }
}
