package com.udea.lis.scan.model.dto;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObservacionDTO {
    private Integer id;
    private String descripcion;
    private String fecha;
    private String autor;
    private Integer problemaId;
}
