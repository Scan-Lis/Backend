package com.udea.lis.scan.service.problemaservice;

import com.udea.lis.scan.model.dto.ProblemaDTO;
import com.udea.lis.scan.model.entity.Reporte;
import com.udea.lis.scan.model.enums.ESala;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface IProblemaService {
    Boolean crearProblema(Reporte reporte);

    ProblemaDTO getProblema(Integer id);

    public Page<ProblemaDTO> getProblemas(Pageable pageable);

    public Page<ProblemaDTO> getProblemasByPc(ESala sala, Integer numeroPc, Pageable pageable);

    public Page<ProblemaDTO> getProblemasBySala(ESala sala, Pageable pageable);

    public Page<ProblemaDTO> getProblemasBySolucionado(Boolean solucionado, Pageable pageable);

    public Page<ProblemaDTO> getProblemasByAuxiliarAsignado(String usuario, Pageable pageable);

    public Page<ProblemaDTO> getProblemasByFechaCreacionBetween(Date fechaInicio, Date fechaFin, Pageable pageable);

    public Page<ProblemaDTO> getProblemasByFechaTerminacionBetween(Date fechaInicio, Date fechaFin, Pageable pageable);

    public void deleteProblema(Integer id);

    public ProblemaDTO solucionarProblema(Integer id, Boolean almacenaado);

    public ProblemaDTO asignarProblema(Integer id, String usuario);

}
