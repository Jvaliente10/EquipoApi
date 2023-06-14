package com.proyect.apiequipo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyect.apiequipo.domain.Partido;
import com.proyect.apiequipo.exception.PartidoNotFoundException;
import com.proyect.apiequipo.repository.PartidoRepository;

@Service
public class PartidoServiceImpl implements PartidoService {

	@Autowired
	private PartidoRepository partidoRepository;

	@Override
	public List<Partido> findAll() {
		return partidoRepository.findAll();
	}

	@Override
	public List<Partido> findByEquipoLocal(String local) {
		return partidoRepository.findByEquipoLocal(local);
	}

	@Override
	public List<Partido> findByEquipoVisitante(String visitante) {
		return partidoRepository.findByEquipoVisitante(visitante);
	}

	@Override
	public List<Partido> findByGanador(String equipoGanador) {
		return partidoRepository.findByGanador(equipoGanador);
	}

	@Override
	public Optional<Partido> findByIdPartido(long id) {
		return partidoRepository.findById(id);
	}

	@Override
	public Partido addPartido(Partido partido) {
		return partidoRepository.save(partido);
	}

	@Override
	public Partido modifyPartido(long id, Partido newPartido) {
		Optional<Partido> optionalPartido = partidoRepository.findById(id);
		if (optionalPartido.isPresent()) {
			Partido partido = optionalPartido.get();
			partido.setFecha(newPartido.getFecha());
			partido.setConvocados(partido.getConvocados());
			partido.setEquipoVisitante(partido.getEquipoVisitante());
			partido.setEquipoVisitante(partido.getEquipoVisitante());
			partido.setNomEquipoLocal(newPartido.getNomEquipoLocal());
			partido.setNomEquipoVisitante(newPartido.getNomEquipoVisitante());
			partido.setGanador(newPartido.getGanador());
			return partidoRepository.save(partido);
		} else {
			throw new PartidoNotFoundException("Partido no encontrado con el id: " + id);
		}
	}

	@Override
	public void deletePartido(long id) {
		Optional<Partido> optionalPartido = partidoRepository.findById(id);
		if (optionalPartido.isPresent()) {
			partidoRepository.delete(optionalPartido.get());
		} else {
			throw new PartidoNotFoundException("Partido no encontrado con el id: " + id);
		}
	}
}
