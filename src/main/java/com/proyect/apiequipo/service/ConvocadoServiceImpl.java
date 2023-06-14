package com.proyect.apiequipo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyect.apiequipo.domain.Convocado;
import com.proyect.apiequipo.domain.ConvocadoId;
import com.proyect.apiequipo.repository.ConvocadoRepository;

@Service
public class ConvocadoServiceImpl implements ConvocadoService {

	@Autowired
	private ConvocadoRepository convocadoRepository;

	@Override
	public List<Convocado> findAll() {
		return convocadoRepository.findAll();
	}

	@Override
	public List<Convocado> findByIdJugador(long idJugador) {
		return convocadoRepository.findByIdJugador(idJugador);
	}

	@Override
	public List<Convocado> findByIdPartido(long idPartido) {
		return convocadoRepository.findByIdPartido(idPartido);
	}

	@Override
	public Convocado addConvocado(Convocado convocado) {
		return convocadoRepository.save(convocado);
	}

	


	@Override
	public Convocado findFirstByOrderByPuntosDesc() {

		return convocadoRepository.findFirstByOrderByPuntosDesc();

	}

	@Override
	public Convocado findFirstByOrderByAsistenciasDesc() {

		return convocadoRepository.findFirstByOrderByAsistenciasDesc();

	}

	@Override
	public Convocado findFirstByOrderByRobosDesc() {

		return convocadoRepository.findFirstByOrderByRobosDesc();

	}

	@Override
	public Convocado findFirstByOrderByRebotesDesc() {

		return convocadoRepository.findFirstByOrderByRebotesDesc();

	}

	@Override
	public Convocado findFirstByOrderByMinutosDesc() {

		return convocadoRepository.findFirstByOrderByMinutosDesc();

	}

	@Override
	public Convocado findFirstByOrderByBloqueosDesc() {

		return convocadoRepository.findFirstByOrderByBloqueosDesc();

	}

	@Override
	public List<Convocado> findByNomJugador(String nom) {

		return convocadoRepository.findByNomJugador(nom);
	}

	@Override
	public Convocado modifyConvocado(ConvocadoId id, Convocado newConvocado) {
		Optional<Convocado> convocadoOptional = convocadoRepository.findById(id);
		if (convocadoOptional.isPresent()) {
			// Realiza las modificaciones necesarias en el objeto Convocado
			Convocado convocado = convocadoOptional.get();
			convocado.setPuntos(newConvocado.getPuntos());
			convocado.setAsistencias(newConvocado.getAsistencias());
			convocado.setBloqueos(newConvocado.getBloqueos());
			convocado.setRebotes(newConvocado.getRebotes());
			convocado.setRobos(newConvocado.getRobos());
			convocado.setFaltas(newConvocado.getFaltas());
			convocado.setMinutos(newConvocado.getMinutos());

			// Guarda el Convocado modificado en la base de datos
			return convocadoRepository.save(convocado);
		}
		return null; // O maneja el caso en el que no se encuentre el Convocado
	}

	@Override
	public void deleteById(ConvocadoId id) {
		convocadoRepository.deleteById(id);
		
	}

}
