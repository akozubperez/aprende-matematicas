package com.github.akozubperez.math;

import com.github.akozubperez.math.arch.auth.entities.Privilege;
import com.github.akozubperez.math.arch.auth.entities.Role;
import com.github.akozubperez.math.arch.auth.entities.RolePrivilege;
import com.github.akozubperez.math.arch.auth.entities.RolePrivilegeId;
import com.github.akozubperez.math.arch.auth.entities.User;
import com.github.akozubperez.math.arch.auth.entities.UserPrivilege;
import com.github.akozubperez.math.arch.auth.entities.UserPrivilegeId;
import com.github.akozubperez.math.arch.auth.entities.UserRole;
import com.github.akozubperez.math.arch.auth.entities.UserRoleId;
import com.github.akozubperez.math.arch.auth.repositories.UserRepository;
import com.github.akozubperez.math.arch.auth.repositories.RoleRepository;
import com.github.akozubperez.math.arch.auth.repositories.PrivilegeRepository;
import com.github.akozubperez.math.arch.auth.repositories.RolePrivilegeRepository;
import com.github.akozubperez.math.arch.auth.repositories.UserPrivilegeRepository;
import com.github.akozubperez.math.arch.auth.repositories.UserRoleRepository;
import com.github.akozubperez.math.arch.auth.services.UserService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;

@Slf4j
@Profile("test-db")
@Configuration
@AllArgsConstructor
public class Initializer implements CommandLineRunner {

    private UserService userService;

    private RoleRepository roleRepository;

    private UserRepository userRepository;

    private UserRoleRepository userRoleRepository;

    private PrivilegeRepository privilegeRepository;

    private RolePrivilegeRepository rolePrivilegeRepository;

    private UserPrivilegeRepository userPrivilegeRepository;

    @Override
    @Transactional
    public void run(String... strings) throws Exception {
        if (privilegeRepository.count() == 0) {
            Privilege writeUserPrivilege = privilegeRepository.save(new Privilege("WRITE_USER"));
            Privilege readUserPrivilege = privilegeRepository.save(new Privilege("READ_USER"));
            Privilege operationPrivilege = privilegeRepository.save(new Privilege("OPERATION"));
            Role adminRole = roleRepository.save(new Role("admin"));
            Role userRole = roleRepository.save(new Role("user"));

            log.info("users: {}", userRepository.count());

            User admin = userRepository.save(new User("david", "David", "dperezcabrera@gmail.com"));
            List<User> users = Arrays.asList(
                    new User("alejandra", "Alejandra", "alejandra.kozubperez@gmail.com")
            );
            users = users.stream().map(userRepository::save).collect(Collectors.toList());
            rolePrivilegeRepository.save(new RolePrivilege(new RolePrivilegeId(adminRole, writeUserPrivilege)));
            rolePrivilegeRepository.save(new RolePrivilege(new RolePrivilegeId(adminRole, readUserPrivilege)));
            rolePrivilegeRepository.save(new RolePrivilege(new RolePrivilegeId(userRole, readUserPrivilege)));
            userPrivilegeRepository.save(new UserPrivilege(new UserPrivilegeId(admin, operationPrivilege)));
            userRoleRepository.save(new UserRole(new UserRoleId(admin, adminRole)));
            users.forEach(u -> userRoleRepository.save(new UserRole(new UserRoleId(u, userRole))));
            log.info("\"admin\" -> {}", userService.getUser(admin.getUsername()));
        }
    }
}
