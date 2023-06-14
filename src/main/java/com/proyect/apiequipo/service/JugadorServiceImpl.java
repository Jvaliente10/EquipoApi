package com.proyect.apiequipo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyect.apiequipo.domain.Jugador;
import com.proyect.apiequipo.repository.JugadorRepository;

@Service
public class JugadorServiceImpl implements JugadorService {

	@Autowired
	private JugadorRepository jugadorRepository;

	@Override
	public List<Jugador> findAll() {
		return jugadorRepository.findAll();
	}

	@Override
	public Optional<Jugador> findById(long id) {
		return jugadorRepository.findById(id);
	}

	@Override
	public Jugador addJugador(Jugador jugador) {
		return jugadorRepository.save(jugador);
	}

	@Override
	public Jugador modifyJugador(long id, Jugador newJugador) {
		Optional<Jugador> jugadorOptional = jugadorRepository.findById(id);
		if (jugadorOptional.isPresent()) {
			Jugador jugador = jugadorOptional.get();
			jugador.setNomJugador(newJugador.getNomJugador());
			jugador.setDorsalJugador(newJugador.getDorsalJugador());
			jugador.setPosicionJugador(newJugador.getPosicionJugador());
			return jugadorRepository.save(jugador);
		} else {
			return null;
		}
	}

	@Override
	public void deleteJugador(long id) {
		jugadorRepository.deleteById(id);
	}



}
