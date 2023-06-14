package com.proyect.apiequipo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyect.apiequipo.user.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findFirstByUsername(String username);

}
