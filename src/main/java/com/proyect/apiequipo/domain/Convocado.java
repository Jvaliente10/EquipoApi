package com.proyect.apiequipo.domain;

import org.springframework.data.annotation.Transient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "convocado")
@IdClass(ConvocadoId.class)
public class Convocado {

    @Id
    private long idJugador;
    
    @Id
    private String nomJugador;

    @Id
    private long idPartido;
    
    @Transient
    @ManyToOne
    @JoinColumn(name = "idJugador", referencedColumnName = "idJugador", insertable = false, updatable = false)
    private Jugador jugador;
    
    @Transient
    @ManyToOne
    @JoinColumn(name = "idPartido", referencedColumnName = "idPartido", insertable = false, updatable = false)
    private Partido partido;

    @Column
    private int puntos;

    @Column
    private int asistencias;
    
    @Column
    private int rebotes;

    @Column
    private int robos;

    @Column
    private int bloqueos;

    @Column
    private int faltas;

    @Column
    private float minutos;
    
}