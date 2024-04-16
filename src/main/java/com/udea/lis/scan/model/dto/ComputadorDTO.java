package com.udea.lis.scan.model.dto;

import com.udea.lis.scan.model.enums.EEstado;
import com.udea.lis.scan.model.enums.ESala;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ComputadorDTO {
    private Integer numeroPc;
    private EEstado estado;
    private ESala sala;
}
