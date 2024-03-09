package com.udea.lis.scan.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String cedula;
    private String correo;
    private String contrasena;
    private Integer rol;
}
