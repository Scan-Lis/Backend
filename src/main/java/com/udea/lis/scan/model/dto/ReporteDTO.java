package com.udea.lis.scan.model.dto;

import com.udea.lis.scan.model.enums.EReporte;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReporteDTO {
    private Integer id;
    private String correo;

    private EReporte tipo;

    private String descripcion;

    private Boolean almacenado;
}
