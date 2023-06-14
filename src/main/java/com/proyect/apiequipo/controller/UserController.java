package com.proyect.apiequipo.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.proyect.apiequipo.security.jwt.JwtProvider;
import com.proyect.apiequipo.service.UserService;
import com.proyect.apiequipo.user.dto.ChangePasswordRequest;
import com.proyect.apiequipo.user.dto.CreateUserRequest;
import com.proyect.apiequipo.user.dto.JwtUserResponse;
import com.proyect.apiequipo.user.dto.LoginRequest;
import com.proyect.apiequipo.user.dto.UserResponse;
import com.proyect.apiequipo.user.model.User;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*",methods = {RequestMethod.GET, RequestMethod.POST})
@RestController
@RequiredArgsConstructor
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;

	@PostMapping("auth/register")
	public ResponseEntity<UserResponse> createUserWithRegistredUserRole(
			@RequestBody CreateUserRequest createUserRequest) {
		logger.info("Inicio registrarUser");
		User user = userService.createUserWithRegistredUserRole(createUserRequest);
		logger.info("Fin registrarUser");
		return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));

	}

	@PostMapping("auth/register/admin")
	public ResponseEntity<UserResponse> createUserWithAdmin(@RequestBody CreateUserRequest createUserRequest) {
		User user = userService.createUserWithAdminRole(createUserRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));

	}

	@PostMapping("/auth/login")
	public ResponseEntity<JwtUserResponse> login(@RequestBody LoginRequest loginRequest) {

		// Realizamos la autenticación

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		// Una vez realizada, la guardamos en el contexto de seguridad
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Devolvemos una respuesta adecuada
		String token = jwtProvider.generateToken(authentication);

		User user = (User) authentication.getPrincipal();

		return ResponseEntity.status(HttpStatus.CREATED).body(JwtUserResponse.of(user, token));

	}

	@PutMapping("/auth/change")
	public ResponseEntity<UserResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,
			@AuthenticationPrincipal User loggedUser) {

		try {
			if (userService.passwordMatch(loggedUser, changePasswordRequest.getOldPassword())) {
				Optional<User> modified = userService.editPassword(loggedUser.getIdUser(),
						changePasswordRequest.getNewPassword());
				if (modified.isPresent()) {
					return ResponseEntity.ok(UserResponse.fromUser(modified.get()));
				} else {
					throw new RuntimeException();
				}
			}
		} catch (RuntimeException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password Data error");

		}
		return null;

	}

}
