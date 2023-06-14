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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.proyect.apiequipo.domain.Equipo;
import com.proyect.apiequipo.exception.EquipoNotFoundException;
import com.proyect.apiequipo.service.EquipoService;

@CrossOrigin(origins = "*",methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController
public class EquipoController {
	private final Logger logger = LoggerFactory.getLogger(EquipoController.class);

	@Autowired
	private EquipoService equipoService;

	@GetMapping("/equipos")
	public ResponseEntity<List<Equipo>> getEquipos(@RequestParam(value = "nombre", defaultValue = "") String nombre) {
		logger.info("Inicio getEquipos");

		List<Equipo> equipos = null;
		if (nombre.equals("")) {
			equipos = equipoService.findAll();
		} else {
			equipos = equipoService.findByName(nombre);
		}

		logger.info("Fin getEquipos");
		return new ResponseEntity<>(equipos, HttpStatus.OK);
	}
	@GetMapping("/equipos/{id}")
	public ResponseEntity<Equipo> getJugadorPorId(@PathVariable Long id) {
		logger.info("Inicio getEquipoPorId");
		Optional<Equipo> equipoOptional = equipoService.findById(id);
		if (equipoOptional.isPresent()) {
			Equipo equipo = equipoOptional.get();
			logger.info("Fin getEquipoPorId");
			return new ResponseEntity<>(equipo, HttpStatus.OK);
		} else {
			logger.error("Equipo no encontrado");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/equipos/puntos")
	public ResponseEntity<List<Equipo>> findByMostPoints() {
		List<Equipo> masPuntos = equipoService.findAllByOrderByPuntosEquipoDesc();
		return new ResponseEntity<>(masPuntos, HttpStatus.OK);
	}

	@PostMapping(value = "/equipos", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Equipo> addEquipo(@RequestBody Equipo equipo) {
		Equipo addedEquipo = equipoService.addEquipo(equipo);
		return new ResponseEntity<>(addedEquipo, HttpStatus.CREATED);
	}

	@PutMapping("/equipos/{id}")
	public ResponseEntity<Equipo> modifyEquipo(@PathVariable long id, @RequestBody Equipo newEquipo) {
		Equipo equipo = equipoService.modifyEquipo(id, newEquipo);
		return new ResponseEntity<>(equipo, HttpStatus.OK);
	}

	@DeleteMapping("/equipos/{id}")
	public ResponseEntity<Response> deleteEquipo(@PathVariable long id) {
		equipoService.deleteEquipo(id);
		return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);

	}

	@ExceptionHandler(EquipoNotFoundException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Response> handleException(EquipoNotFoundException pnfe) {
		Response response = Response.errorResponse(Response.NOT_FOUND, pnfe.getMessage());
		logger.error(pnfe.getMessage(), pnfe);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

}
