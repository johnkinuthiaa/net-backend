package com.slippery.netbackend.repository;

import com.slippery.netbackend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {
    Users findByUsername(String username);
}
