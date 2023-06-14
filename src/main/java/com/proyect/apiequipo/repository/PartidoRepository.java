package com.proyect.apiequipo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyect.apiequipo.domain.Partido;

public interface PartidoRepository extends JpaRepository<Partido, Long> {

	List<Partido> findByEquipoLocal(String equipoLocal);
	List<Partido> findByEquipoVisitante(String equipoVisitante);
	List<Partido> findByGanador(String equipoGanador);

}
