package com.proyect.apiequipo.service;

import java.util.List;
import java.util.Optional;
import com.proyect.apiequipo.domain.Partido;

public interface PartidoService {
	List<Partido> findAll();

	List<Partido> findByEquipoLocal(String equipoLocal);
	List<Partido> findByEquipoVisitante(String equipoVisitante);

	List<Partido> findByGanador(String equipoGanador);

	Optional<Partido> findByIdPartido(long id);
	Partido addPartido(Partido partido);

	Partido modifyPartido(long id, Partido partido);

	void deletePartido(long id);

}
