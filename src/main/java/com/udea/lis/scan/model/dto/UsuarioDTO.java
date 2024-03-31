package com.udea.lis.scan.model.dto;

import lombok.*;

@Data
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String cedula;
    private String correo;
    private String contrasena;

    @NonNull
    private String rol;
}
