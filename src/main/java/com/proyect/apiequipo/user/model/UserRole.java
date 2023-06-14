package com.proyect.apiequipo.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UserRole {

	ADMIN("admin"), REGISTEDUSER("registeduser");

	private String typeUser;

	public static UserRole convert(String type) {
		return Arrays.stream(UserRole.values()).filter(role -> role.typeUser.equalsIgnoreCase(type)).findFirst()
				.orElse(null);
	}
}
