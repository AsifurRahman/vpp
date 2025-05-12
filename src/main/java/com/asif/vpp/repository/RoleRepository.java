package com.asif.vpp.repository;

import com.asif.vpp.generic.repository.AbstractRepository;
import com.asif.vpp.model.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends AbstractRepository<Role> {

    Optional<Role> findByNameIgnoreCase(String name);
}
