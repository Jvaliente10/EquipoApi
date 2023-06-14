package com.proyect.apiequipo.service;

import java.util.List;
import java.util.Optional;
import com.proyect.apiequipo.domain.Jugador;


public interface JugadorService {

	List<Jugador> findAll();

	Optional<Jugador> findById(long id);

	Jugador addJugador(Jugador jugador);

	Jugador modifyJugador(long id, Jugador newJugador);

	void deleteJugador(long id);

}
