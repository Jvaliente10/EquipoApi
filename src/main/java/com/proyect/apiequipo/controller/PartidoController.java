package com.proyect.apiequipo.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyect.apiequipo.domain.Partido;
import com.proyect.apiequipo.exception.PartidoNotFoundException;
import com.proyect.apiequipo.service.PartidoService;

@CrossOrigin(origins = "*",methods = {RequestMethod.GET, RequestMethod.POST,RequestMethod.PUT,
		RequestMethod.DELETE})
@RestController
@RequestMapping("/partidos")
public class PartidoController {
	private final Logger logger = LoggerFactory.getLogger(PartidoController.class);

	@Autowired
	private PartidoService partidoService;

	@GetMapping
	public ResponseEntity<List<Partido>> getPartidos() {
		logger.info("Inicio getPartidos");
		List<Partido> partidos = partidoService.findAll();
		logger.info("Fin getPartidos");
		return new ResponseEntity<>(partidos, HttpStatus.OK);
		
	}

	@GetMapping("/{id}")
	public ResponseEntity<Partido> getPartido(@PathVariable("id") long id) {
		logger.info("Inicio getPartido id:{}", id);
		Optional<Partido> partido = partidoService.findByIdPartido(id);
		if (partido.isPresent()) {
			logger.info("Fin getPartido id:{}", id);
			return new ResponseEntity<>(partido.get(), HttpStatus.OK);
		} else {
			logger.error("Partido con id:{} no encontrado", id);
			throw new PartidoNotFoundException("Partido no encontrado");
		}
	}

	@GetMapping(params = "visitante")
	public ResponseEntity<List<Partido>> findByVisitante(@RequestParam("visitante") String equipo) {
		logger.info("Inicio findByEquipo equipo:{}", equipo); 
		List<Partido> partidos = partidoService.findByEquipoVisitante(equipo);
		logger.info("Fin findByEquipo equipo:{}", equipo);
		return new ResponseEntity<>(partidos, HttpStatus.OK);
	}


	@GetMapping(params = "ganador")
	public ResponseEntity<List<Partido>> findByGanador(@RequestParam("ganador") String equipoGanador) {
		logger.info("Inicio findByGanador equipoGanador:{}", equipoGanador);
		List<Partido> partidos = partidoService.findByGanador(equipoGanador);
		logger.info("Fin findByGanador equipoGanador:{}", equipoGanador);
		return new ResponseEntity<>(partidos, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Partido> addPartido(@RequestBody Partido partido) {
		logger.info("Inicio addPartido");
		Partido addedPartido = partidoService.addPartido(partido);
		logger.info("Fin addPartido");
		return new ResponseEntity<>(addedPartido, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Partido> modifyPartido(@PathVariable("id") long id, @RequestBody Partido partido) {
		Partido modifiedPartido = partidoService.modifyPartido(id, partido);
		if (modifiedPartido != null) {
			return new ResponseEntity<>(modifiedPartido, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePartido(@PathVariable("id") long id) {
		partidoService.deletePartido(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
