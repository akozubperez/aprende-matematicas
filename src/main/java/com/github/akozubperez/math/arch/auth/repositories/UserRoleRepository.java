package com.github.akozubperez.math.arch.auth.repositories;

import com.github.akozubperez.math.arch.auth.entities.UserRole;
import com.github.akozubperez.math.arch.auth.entities.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

}
