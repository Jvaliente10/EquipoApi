package com.proyect.apiequipo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service("userDetailService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserService userService;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return userService.findByUserName(username).orElseThrow(()-> new UsernameNotFoundException("No existe ningún usuario con el nombre: " + username ));

	}
}
