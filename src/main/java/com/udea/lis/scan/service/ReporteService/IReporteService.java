package com.udea.lis.scan.service.ReporteService;

import com.udea.lis.scan.model.dto.ReporteDTO;

public interface IReporteService {
    public Iterable<ReporteDTO> getReportes();

    public ReporteDTO saveReporte(ReporteDTO reporteDTO);

    public ReporteDTO deleteReporte(Integer id);

    public Iterable<ReporteDTO> getReportesBySala(String sala);

    public Iterable<ReporteDTO> getReportesByTipo(String tipo);

    public Iterable<ReporteDTO> getreporteByalmacenado(Boolean almacenado);



}
