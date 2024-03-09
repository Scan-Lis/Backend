package com.udea.lis.scan.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @Column(name = "usuario_id", nullable = false)
    private Integer id;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "cedula", length = 12)
    private String cedula;

    @Column(name = "correo", length = 50)
    private String correo;

    @Column(name = "contrasena", length = 255)
    private String contrasena;

    @Column(name = "rol")
    private Integer rol;
}
