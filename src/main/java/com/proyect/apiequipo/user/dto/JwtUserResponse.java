package com.proyect.apiequipo.user.dto;

import com.proyect.apiequipo.user.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class JwtUserResponse extends UserResponse {

	private String token;

	public JwtUserResponse(UserResponse userResponse) {
		this.id = userResponse.id;
		this.username = userResponse.username;
		this.createdAt = userResponse.createdAt;
		this.role= userResponse.role;

	}

	public static JwtUserResponse of(User user, String token) {
		JwtUserResponse result = new JwtUserResponse(UserResponse.fromUser(user));
		result.setToken(token);
		return result;
	}

}
