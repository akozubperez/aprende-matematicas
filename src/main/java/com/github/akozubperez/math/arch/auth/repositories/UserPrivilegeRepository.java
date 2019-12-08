package com.github.akozubperez.math.arch.auth.repositories;

import com.github.akozubperez.math.arch.auth.entities.UserPrivilege;
import com.github.akozubperez.math.arch.auth.entities.UserPrivilegeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPrivilegeRepository extends JpaRepository<UserPrivilege, UserPrivilegeId> {

}
