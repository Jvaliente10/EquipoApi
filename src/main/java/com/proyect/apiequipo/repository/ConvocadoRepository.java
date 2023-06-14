package com.proyect.apiequipo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.proyect.apiequipo.domain.Convocado;
import com.proyect.apiequipo.domain.ConvocadoId;

public interface ConvocadoRepository extends CrudRepository<Convocado, ConvocadoId> {

	List<Convocado> findAll();

	Optional<Convocado> findById(ConvocadoId id);

	Convocado findFirstByOrderByPuntosDesc();

	Convocado findFirstByOrderByAsistenciasDesc();

	Convocado findFirstByOrderByRobosDesc();

	Convocado findFirstByOrderByRebotesDesc();

	Convocado findFirstByOrderByMinutosDesc();

	Convocado findFirstByOrderByBloqueosDesc();

	List<Convocado> findByNomJugador(String nom);

	List<Convocado> findByIdJugador(long idJugador);

	List<Convocado> findByIdPartido(long idPartido);

}
