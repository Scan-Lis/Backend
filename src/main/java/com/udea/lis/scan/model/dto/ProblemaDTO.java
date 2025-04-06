package com.udea.lis.scan.model.dto;


import com.udea.lis.scan.model.enums.ESala;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemaDTO {
    private Integer id;
    private String descripcionBase;
    private Boolean solucionado;
    private Date fechaCreacion;
    private Date fechaTerminacion;

    private String auxiliarAsignado;
    private ESala sala;
    private Integer numeroPc;
}