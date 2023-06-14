package com.proyect.apiequipo.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.proyect.apiequipo.security.errorhandling.JwtAccessDeniedHandler;
import com.proyect.apiequipo.security.errorhandling.JwtAuthenticationEntryPoint;
import com.proyect.apiequipo.security.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);

		AuthenticationManager authenticationManager = authenticationManagerBuilder
				.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder).and().build();

		return authenticationManager;

	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()

				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)

				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and().cors().configurationSource(request -> {
					var cors = new CorsConfiguration();

					cors.setAllowedOrigins(List.of("*"));
					cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
					cors.setAllowedHeaders(List.of("*"));
					return cors;
				}).and().authorizeHttpRequests()
				.requestMatchers("/jugadores/**", "/convocados/**", "/convocados/{nombre}", "convocados/partido/{id}")
				.hasAnyRole("ADMIN", "REGISTEDUSER").requestMatchers("/auth/register/admin").hasRole("ADMIN")
				.anyRequest().authenticated();

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		http.headers().frameOptions().disable();

		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/auth/register", "/auth/login", "/convocados/**",
				"/partidos/**", "/equipos/**","/auth/register/admin");
	}

}
