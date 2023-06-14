package com.proyect.apiequipo.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.proyect.apiequipo.domain.Convocado;
import com.proyect.apiequipo.domain.ConvocadoId;
import com.proyect.apiequipo.exception.ConvocadoNotFoundException;
import com.proyect.apiequipo.service.ConvocadoService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController
@RequestMapping("/convocados")
public class ConvocadoController {
	private final Logger logger = LoggerFactory.getLogger(ConvocadoController.class);

	@Autowired
	private ConvocadoService convocadoService;

	@GetMapping
	public ResponseEntity<List<Convocado>> findAll() {
		List<Convocado> convocados = convocadoService.findAll();
		System.out.println("HOLA");
		return new ResponseEntity<>(convocados, HttpStatus.OK);
	}

	@GetMapping("/puntos")
	public ResponseEntity<Convocado> findByMostPoints() {
		Convocado masPuntos = convocadoService.findFirstByOrderByPuntosDesc();
		return new ResponseEntity<>(masPuntos, HttpStatus.OK);
	}

	@GetMapping("/asistencias")
	public ResponseEntity<Convocado> findByMostAssists() {
		Convocado masPuntos = convocadoService.findFirstByOrderByAsistenciasDesc();
		return new ResponseEntity<>(masPuntos, HttpStatus.OK);
	}

	@GetMapping("/robos")
	public ResponseEntity<Convocado> findByMostSteals() {
		Convocado masPuntos = convocadoService.findFirstByOrderByRobosDesc();
		return new ResponseEntity<>(masPuntos, HttpStatus.OK);
	}

	@GetMapping("/rebotes")
	public ResponseEntity<Convocado> findByMostRebounds() {
		Convocado masPuntos = convocadoService.findFirstByOrderByRebotesDesc();
		return new ResponseEntity<>(masPuntos, HttpStatus.OK);
	}

	@GetMapping("/minutos")
	public ResponseEntity<Convocado> findByMostMinutes() {
		Convocado masPuntos = convocadoService.findFirstByOrderByMinutosDesc();
		return new ResponseEntity<>(masPuntos, HttpStatus.OK);
	}

	@GetMapping("/bloqueos")
	public ResponseEntity<Convocado> findByMostBlocks() {
		Convocado masPuntos = convocadoService.findFirstByOrderByBloqueosDesc();
		return new ResponseEntity<>(masPuntos, HttpStatus.OK);
	}

	@GetMapping("/{nombre}")
	public ResponseEntity<List<Convocado>> getConvocadosByNombre(@PathVariable("nombre") String nombre) {
		logger.info("Inicio convocados por nombre");
		List<Convocado> nomConvocado = convocadoService.findByNomJugador(nombre);
		logger.info("Fin convocados por nombre");
		return new ResponseEntity<>(nomConvocado, HttpStatus.OK);

	}

	@GetMapping("/jugador/{id}")
	public ResponseEntity<List<Convocado>> findByJugador(@PathVariable("id") long idJugador) {
		List<Convocado> convocados = convocadoService.findByIdJugador(idJugador);
		return new ResponseEntity<>(convocados, HttpStatus.OK);
	}

	@GetMapping("/partido/{id}")
	public ResponseEntity<List<Convocado>> findByPartido(@PathVariable("id") long idPartido) {
		List<Convocado> convocados = convocadoService.findByIdPartido(idPartido);
		return new ResponseEntity<>(convocados, HttpStatus.OK);
	}

	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Convocado> addConvocado(@RequestBody Convocado convocado) {
		Convocado addedConvocado = convocadoService.addConvocado(convocado);
		return new ResponseEntity<>(addedConvocado, HttpStatus.CREATED);
	}

	@PutMapping("/{idPartido}/{nomJugador}/{idJugador}")
	public ResponseEntity<Convocado> updateConvocado(@PathVariable("idPartido") long idPartido,
			@PathVariable("nomJugador") String nomJugador, @PathVariable("idJugador") long idJugador,
			@RequestBody Convocado convocado) {
		ConvocadoId convocadoId = new ConvocadoId(idPartido, nomJugador, idJugador);
		Convocado updatedConvocado = convocadoService.modifyConvocado(convocadoId, convocado);
		return new ResponseEntity<>(updatedConvocado, HttpStatus.OK);
	}


	@DeleteMapping("/{idPartido}/{nomJugador}/{idJugador}")
	public ResponseEntity<String> deleteConvocado(@PathVariable("idPartido") long idPartido,
			@PathVariable("nomJugador") String nomJugador, @PathVariable("idJugador") long idJugador) {
		ConvocadoId convocado = new ConvocadoId(idPartido, nomJugador, idJugador);
		convocadoService.deleteById(convocado);
		System.out.println("Hola");
		return new ResponseEntity<>("Convocado eliminado correctamente", HttpStatus.OK);
	}

	@ExceptionHandler(ConvocadoNotFoundException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Response> handleException(ConvocadoNotFoundException pnfe) {
		Response response = Response.errorResponse(Response.NOT_FOUND, pnfe.getMessage());
		logger.error(pnfe.getMessage(), pnfe);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

}
