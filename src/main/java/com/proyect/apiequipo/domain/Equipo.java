package com.proyect.apiequipo.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "equipo")
public class Equipo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idEquipo;

	@Column
	private String nombreEquipo;

	@Column
	private int puntosEquipo;
	
	@OneToMany(mappedBy = "equipoLocal", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Partido> partidosLocal;
	@OneToMany(mappedBy = "equipoVisitante", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Partido> partidosVisitante;

	
	

}
