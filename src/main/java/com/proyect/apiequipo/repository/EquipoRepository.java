package com.proyect.apiequipo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyect.apiequipo.domain.Equipo;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    
    List<Equipo> findByNombreEquipo(String nombreEquipo);
    
    Optional<Equipo> findByIdEquipo(long idEquipo);
    
    List<Equipo> findAllByOrderByPuntosEquipoDesc();

}
