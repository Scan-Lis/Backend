package com.udea.lis.scan.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "Computador")
public class Computador {
    @Id
    @Column(name = "computador_id", nullable = false)
    private String id;

    @Column(name = "estado", length = 20)
    private String estado;

    @Column(name = "sala", length = 20)
    private String sala;

}
