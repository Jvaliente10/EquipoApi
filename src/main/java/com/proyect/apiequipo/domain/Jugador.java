package com.proyect.apiequipo.domain;

import java.util.List;
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
@Entity(name="jugador")

public class Jugador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idJugador;
	
	@Column
	private String nomJugador;
	
	@Column
	private int dorsalJugador;
	
	@Column
	private String posicionJugador;
	
	@OneToMany(mappedBy = "jugador",fetch = FetchType.EAGER)
    List<Convocado> convocados;
	
	
	@Override

	public String toString() {

	return "Jugador [nomJugador=" + nomJugador + "]";

	}
}
