package com.proyect.apiequipo.domain;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ConvocadoId implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Column(name = "jugador_id")
    private long idJugador;
    
    @Column
    private String nomJugador;
	
    @Column(name = "partido_id")
    private long idPartido;


    
    public long getJugadorId() {
        return idJugador;
    }

    public long getPartidoId() {
        return idPartido;
    }

}