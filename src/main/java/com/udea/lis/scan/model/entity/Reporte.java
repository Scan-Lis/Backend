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
@Table(name = "Reporte")
public class Reporte {

    @Id
    @Column(name = "reporte_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "correo", length = 50)
    private String correo;

    @Column(name = "tipo", length = 50)
    private String tipo;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "almacenado")
    private Boolean almacenado;

    @Column(name = "fecha", length = 50)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "fk_pc", nullable = false)
    private Computador computador;
}
