package com.udea.lis.scan.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
@Entity
@Table(name = "Observacion")
public class Observacion {

    @Id
    @Column(name = "observacion_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "descripcion", length = 1000)
    private String descripcion;

    @Column(name = "fecha", length = 50)
    private Date fecha;

    @Column(name = "autor", length = 50)
    private String autor;
    
    @ManyToOne
    @JoinColumn(name = "fk_problema", nullable = false)
    private Problema problema;
}
