package com.proyect.apiequipo.service;

import java.util.List;

import com.proyect.apiequipo.domain.Convocado;
import com.proyect.apiequipo.domain.ConvocadoId;

public interface ConvocadoService {
	List<Convocado> findAll();

	List<Convocado> findByIdJugador(long idJugador);

	List<Convocado> findByIdPartido(long idPartido);

	List<Convocado> findByNomJugador(String nom);

	Convocado findFirstByOrderByPuntosDesc();

	Convocado findFirstByOrderByAsistenciasDesc();

	Convocado findFirstByOrderByRobosDesc();

	Convocado findFirstByOrderByRebotesDesc();

	Convocado findFirstByOrderByMinutosDesc();

	Convocado findFirstByOrderByBloqueosDesc();

	Convocado addConvocado(Convocado convocado);

	void deleteById(ConvocadoId id);

	Convocado modifyConvocado(ConvocadoId id, Convocado newConvocado);

}
