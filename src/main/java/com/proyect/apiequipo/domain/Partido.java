package com.proyect.apiequipo.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "partido")
public class Partido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPartido;

	@Column
	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Column
	private String ganador;
	
	@Column 
	private String nomEquipoVisitante;
	
	@Column
	private String nomEquipoLocal;

	@JsonIgnore
	@ManyToOne
	@JoinColumn
	private Equipo equipoVisitante;

	@JsonIgnore
	@ManyToOne
	@JoinColumn
	private Equipo equipoLocal;

	@OneToMany(mappedBy = "partido", fetch = FetchType.EAGER)
	List<Convocado> convocados;


}
