package com.asif.vpp.repository;

import com.asif.vpp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Set<User> findAllByIdInAndIsActive(Set<Long> ids, Boolean isActive);

}
