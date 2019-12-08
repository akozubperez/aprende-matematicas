package com.github.akozubperez.math.arch.auth.repositories;

import com.github.akozubperez.math.arch.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
