package com.proyect.apiequipo.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.proyect.apiequipo.domain.Jugador;
import com.proyect.apiequipo.exception.JugadorNotFoundException;
import com.proyect.apiequipo.service.JugadorService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController
public class JugadorController {
	private final Logger logger = LoggerFactory.getLogger(JugadorController.class);

	@Autowired
	private JugadorService jugadorService;

	@GetMapping("/jugadores")
	public ResponseEntity<List<Jugador>> getJugadores() {
		logger.info("Inicio getJugadores");
		List<Jugador> jugadores = null;

		jugadores = jugadorService.findAll();

		logger.info("Fin getJugadores");
		return new ResponseEntity<>(jugadores, HttpStatus.OK);

	}

	@GetMapping("/jugadores/{id}")
	public ResponseEntity<Jugador> getJugadorPorId(@PathVariable Long id) {
		logger.info("Inicio getJugadorPorId");
		Optional<Jugador> jugadorOptional = jugadorService.findById(id);
		if (jugadorOptional.isPresent()) {
			Jugador jugador = jugadorOptional.get();
			logger.info("Fin getJugadorPorId");
			return new ResponseEntity<>(jugador, HttpStatus.OK);
		} else {
			logger.error("Jugador no encontrado");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/jugadores", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Jugador> addJugador(@RequestBody Jugador jugador) {
		Jugador addedJugador = jugadorService.addJugador(jugador);
		return new ResponseEntity<>(addedJugador, HttpStatus.CREATED);
	}

	@PutMapping("/jugadores/{id}")
	public ResponseEntity<Jugador> modifyJugador(@PathVariable long id, @RequestBody Jugador newJugador) {
		Jugador jugador = jugadorService.modifyJugador(id, newJugador);
		return new ResponseEntity<>(jugador, HttpStatus.OK);
	}

	@DeleteMapping("/jugadores/{id}")
	public ResponseEntity<Response> deleteProduct(@PathVariable long id) {
		jugadorService.deleteJugador(id);
		return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
	}

	@ExceptionHandler(JugadorNotFoundException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Response> handleException(JugadorNotFoundException pnfe) {
		Response response = Response.errorResponse(Response.NOT_FOUND, pnfe.getMessage());
		logger.error(pnfe.getMessage(), pnfe);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
}
