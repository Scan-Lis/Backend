package com.udea.lis.scan.controller.requestModel;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ObservacionRequest {
    private String observacion;
    private String autor;
}
