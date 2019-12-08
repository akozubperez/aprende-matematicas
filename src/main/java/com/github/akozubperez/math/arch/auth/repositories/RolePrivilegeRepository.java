package com.github.akozubperez.math.arch.auth.repositories;

import com.github.akozubperez.math.arch.auth.entities.RolePrivilege;
import com.github.akozubperez.math.arch.auth.entities.RolePrivilegeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePrivilegeRepository extends JpaRepository<RolePrivilege, RolePrivilegeId> {

}
