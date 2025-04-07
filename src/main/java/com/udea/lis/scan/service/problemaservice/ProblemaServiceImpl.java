package com.udea.lis.scan.service.problemaservice;

import com.udea.lis.scan.error.ProblemaNotFoundException;
import com.udea.lis.scan.model.dto.ProblemaDTO;
import com.udea.lis.scan.model.entity.Problema;
import com.udea.lis.scan.model.entity.Reporte;
import com.udea.lis.scan.model.enums.ESala;
import com.udea.lis.scan.model.mapper.ProblemaMapper;
import com.udea.lis.scan.model.repository.ProblemaRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class ProblemaServiceImpl implements IProblemaService {

    private ProblemaRepository problemaRepository;
    private ProblemaMapper problemaMapper;
    public Boolean crearProblema(Reporte reporte) {
        Problema problema = new Problema();
        problema.setDescripcionBase(reporte.getDescripcion());
        problema.setSolucionado(false);
        problema.setFechaCreacion(new Date());
        problema.setFechaTerminacion(null);
        problema.setAuxiliarAsignado(null);
        problema.setComputador(reporte.getComputador());
        Problema problemaCreado = problemaRepository.save(problema);
        return problemaRepository.existsById(problemaCreado.getId());
    }

    @Override
    public ProblemaDTO getProblema(Integer id) {
        return null;
    }

    @Override
    public Page<ProblemaDTO> getProblemas(Pageable pageable) {
        Page<Problema> listaProblemas = problemaRepository.findAll(pageable);
        if (listaProblemas.getTotalElements() == 0){ // si definitivamente no hay reportes
            throw new ProblemaNotFoundException("no se encontraron reportes");
        }
        return new PageImpl<>(problemaMapper.toProblemasDTOList(listaProblemas.getContent()), pageable, listaProblemas.getTotalElements());
    }

    @Override
    public Page<ProblemaDTO> getProblemasByPc(ESala sala, Integer numeroPc, Pageable pageable) {
        return null;
    }

    @Override
    public Page<ProblemaDTO> getProblemasBySala(ESala sala, Pageable pageable) {
        return null;
    }

    @Override
    public Page<ProblemaDTO> getProblemasBySolucionado(Boolean solucionado, Pageable pageable) {
        return null;
    }

    @Override
    public Page<ProblemaDTO> getProblemasByAuxiliarAsignado(String usuario, Pageable pageable) {
        return null;
    }

    @Override
    public void deleteProblema(Integer id) {

    }

    @Override
    public ProblemaDTO solucionarProblema(Integer id, Boolean almacenaado) {
        return null;
    }
}
