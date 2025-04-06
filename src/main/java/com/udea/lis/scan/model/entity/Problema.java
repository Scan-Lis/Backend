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
@Table(name = "Problema")
public class Problema {
    @Id
    @Column(name = "problema_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "descripcionBase", length = 500)
    private String descripcionBase;

    @Column(name = "solucionado")
    private Boolean solucionado;

    @Column(name = "fechaCreacion")
    private Date fechaCreacion;

    @Column(name = "fechaTerminacion")
    private Date fechaTerminacion;

    @ManyToOne
    @JoinColumn(name = "fk_aux", nullable = true)
    private Usuario auxiliarAsignado;

    @ManyToOne
    @JoinColumn(name = "fk_pc", nullable = false)
    private Computador computador;

}
