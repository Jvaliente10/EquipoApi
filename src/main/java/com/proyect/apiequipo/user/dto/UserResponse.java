package com.proyect.apiequipo.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.proyect.apiequipo.user.model.User;
import com.proyect.apiequipo.user.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserResponse {

	protected String id;
	protected String username;
	protected Set<UserRole> role;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	protected LocalDateTime createdAt;

	public static UserResponse fromUser(User user) {

		return UserResponse.builder().id(user.getIdUser().toString()).username(user.getUsername())
				.createdAt(user.getCreatedAt()).role(user.getRoles()).build();
	}

}