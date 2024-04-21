package com.udea.lis.scan.service.reporteservice;

import com.udea.lis.scan.model.dto.ReporteDTO;
import com.udea.lis.scan.model.enums.EReporte;
import com.udea.lis.scan.model.enums.ESala;

import java.util.Date;
import java.util.List;

public interface IReporteService {
    public ReporteDTO getReporte(Integer id);

    public List<ReporteDTO> getReportes();

    public List<ReporteDTO> getReportesByPc(ESala sala, Integer numeroPc);

    public List<ReporteDTO> getReportesBySala(ESala sala);

    public List<ReporteDTO> getReportesByTipo(EReporte tipo);

    public List<ReporteDTO> getReporteByAlmacenado(Boolean almacenado);

    public ReporteDTO saveReporte(ReporteDTO reporteDTO);

    public void deleteReporte(Integer id);

    public ReporteDTO almacenarReporte(Integer id, Boolean almacenaado);

}
