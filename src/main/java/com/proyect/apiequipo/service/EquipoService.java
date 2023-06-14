package com.proyect.apiequipo.service;

import java.util.List;
import java.util.Optional;
import com.proyect.apiequipo.domain.Equipo;

public interface EquipoService {

	List<Equipo> findAll();

	List<Equipo> findByName(String nombre);
	
	List<Equipo> findAllByOrderByPuntosEquipoDesc();

	Optional<Equipo> findById(long id);

	Equipo addEquipo(Equipo equipo);

	Equipo modifyEquipo(long id, Equipo newEquipo);

	void deleteEquipo(long id);

}
