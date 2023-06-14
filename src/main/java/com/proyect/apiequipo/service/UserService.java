package com.proyect.apiequipo.service;


import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyect.apiequipo.repository.UserRepository;
import com.proyect.apiequipo.user.dto.CreateUserRequest;
import com.proyect.apiequipo.user.model.User;
import com.proyect.apiequipo.user.model.UserRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public User createUser(CreateUserRequest createUserRequest, Set<UserRole> roles) {
		User user = User.builder().username(createUserRequest.getUsername())
				.password(passwordEncoder.encode(createUserRequest.getPassword())).roles(roles).build();
		return userRepository.save(user);

	}

	public User createUserWithRegistredUserRole(CreateUserRequest createUserRequest) {
		return createUser(createUserRequest, EnumSet.of(UserRole.REGISTEDUSER));
	}

	public User createUserWithAdminRole(CreateUserRequest createUserRequest) {
		return createUser(createUserRequest, EnumSet.of(UserRole.ADMIN));
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public Optional<User> findById(UUID id) {
		return userRepository.findById(id);
	}

	public Optional<User> findByUserName(String userName) {
		return userRepository.findFirstByUsername(userName);
	}

	public Optional<User> editPassword(UUID userId, String newPassword) {

		return userRepository.findById(userId).map(u -> {
			u.setPassword(passwordEncoder.encode(newPassword));
			return userRepository.save(u);
		});

	}

	public void deleteById(UUID  id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
			;
		}
	}

	public void delete(User user) {
		deleteById(user.getIdUser());
	}

	public boolean passwordMatch(User user, String password) {
		return passwordEncoder.matches(password, user.getPassword());

	}

}
