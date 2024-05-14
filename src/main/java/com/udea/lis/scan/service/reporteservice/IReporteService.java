package com.udea.lis.scan.service.reporteservice;

import com.udea.lis.scan.model.dto.ReporteDTO;
import com.udea.lis.scan.model.enums.EReporte;
import com.udea.lis.scan.model.enums.ESala;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface IReporteService {
    public ReporteDTO getReporte(Integer id);

    public Page<ReporteDTO> getReportes(Pageable pageable);

    public Page<ReporteDTO> getReportesByPc(ESala sala, Integer numeroPc, Pageable pageable);

    public Page<ReporteDTO> getReportesBySala(ESala sala, Pageable pageable);

    public Page<ReporteDTO> getReportesByTipo(EReporte tipo, Pageable pageable);

    public Page<ReporteDTO> getReporteByAlmacenado(Boolean almacenado, Pageable pageable);

    public ReporteDTO saveReporte(ReporteDTO reporteDTO);

    public void deleteReporte(Integer id);

    public ReporteDTO almacenarReporte(Integer id, Boolean almacenaado);

}
