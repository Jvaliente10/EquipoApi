package com.proyect.apiequipo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyect.apiequipo.domain.Equipo;
import com.proyect.apiequipo.repository.EquipoRepository;

@Service
public class EquipoServiceImpl implements EquipoService {

	@Autowired
	private EquipoRepository equipoRepository;

	@Override
	public List<Equipo> findAll() {
		return equipoRepository.findAll();
	}

	@Override
	public List<Equipo> findByName(String nombre) {
		return equipoRepository.findByNombreEquipo(nombre);
	}

	@Override
	public Optional<Equipo> findById(long id) {
		return equipoRepository.findById(id);
	}

	@Override
	public Equipo addEquipo(Equipo equipo) {
		return equipoRepository.save(equipo);
	}

	@Override
	public Equipo modifyEquipo(long id, Equipo newEquipo) {
		Optional<Equipo> equipo = equipoRepository.findById(id);
		if (equipo.isPresent()) {
			Equipo modifiedEquipo = equipo.get();
			modifiedEquipo.setNombreEquipo(newEquipo.getNombreEquipo());
			modifiedEquipo.setPuntosEquipo(newEquipo.getPuntosEquipo());
			return equipoRepository.save(modifiedEquipo);
		}
		return null;
	}

	@Override
	public void deleteEquipo(long id) {
		equipoRepository.deleteById(id);
	}

	@Override
	public List<Equipo> findAllByOrderByPuntosEquipoDesc() {
		
		return equipoRepository.findAllByOrderByPuntosEquipoDesc();
	}

}

