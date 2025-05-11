package com.udea.lis.scan.service.problemaservice;

import com.udea.lis.scan.model.dto.ObservacionDTO;
import com.udea.lis.scan.model.dto.ProblemaDTO;
import com.udea.lis.scan.model.entity.Reporte;
import com.udea.lis.scan.model.enums.ESala;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface IProblemaService {


    Boolean crearProblema(Reporte reporte);

    ProblemaDTO getProblema(Integer id);

    //controlado
    public Page<ProblemaDTO> getProblemas(Pageable pageable);

    //controlado
    public Page<ProblemaDTO> getProblemasByPc(ESala sala, Integer numeroPc, Pageable pageable);


    //controlado
    public Page<ProblemaDTO> getProblemasBySala(ESala sala, Pageable pageable);


    //controlado
    public Page<ProblemaDTO> getProblemasBySolucionado(Boolean solucionado, Pageable pageable);

    //controlado
    public Page<ProblemaDTO> getProblemasByAuxiliarAsignado(String usuario, Pageable pageable);

    //controlado
    public Page<ProblemaDTO> getProblemasByFechaCreacionBetween(Date fechaInicio, Date fechaFin, Pageable pageable);

    //controlado
    public Page<ProblemaDTO> getProblemasByFechaTerminacionBetween(Date fechaInicio, Date fechaFin, Pageable pageable);

    //controlado
    public void deleteProblema(Integer id);

    //controlado
    public ProblemaDTO solucionarProblema(Integer id, Boolean solucionado, String descripcionSolucion);

    //controlado
    public void asignarProblema(Integer id, String usuario);

    //agregarObservacion
    public ObservacionDTO agregarObservacion(Integer idProblema, String observacion, String autor);

}
