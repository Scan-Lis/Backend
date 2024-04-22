package com.udea.lis.scan.model.dto;

import com.udea.lis.scan.model.enums.EReporte;
import com.udea.lis.scan.model.enums.ESala;
import lombok.*;

import java.util.Date;

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
    private Date fecha;
    private ESala sala;
    private Integer numeroPc;
}
