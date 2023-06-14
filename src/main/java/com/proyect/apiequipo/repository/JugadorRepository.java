package com.proyect.apiequipo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyect.apiequipo.domain.Jugador;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {
	
}
