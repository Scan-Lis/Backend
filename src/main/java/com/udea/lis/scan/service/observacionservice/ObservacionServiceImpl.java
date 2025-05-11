package com.udea.lis.scan.service.observacionservice;

import com.udea.lis.scan.error.ObservacionNotFoundException;
import com.udea.lis.scan.error.ProblemaNotFoundException;
import com.udea.lis.scan.model.dto.ObservacionDTO;
import com.udea.lis.scan.model.entity.Observacion;
import com.udea.lis.scan.model.entity.Problema;
import com.udea.lis.scan.model.mapper.ObservacionMapper;
import com.udea.lis.scan.model.repository.ObservacionRepository;
import com.udea.lis.scan.model.repository.ProblemaRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ObservacionServiceImpl implements IObservacionService {

    private ObservacionRepository observacionRepository;
    private ProblemaRepository problemaRepository;
    private ObservacionMapper observacionMapper;

    @Override
    public Page<ObservacionDTO> findAll(Pageable pageable) {
        Page<Observacion> listaObservaciones = observacionRepository.findAll(pageable);
        if (listaObservaciones.getTotalElements() == 0) {
            throw new ObservacionNotFoundException("no se encontraron observaciones");
        }
        return new PageImpl<>(observacionMapper.toObservacionDTOList(listaObservaciones.getContent()), pageable, listaObservaciones.getTotalElements());
    }

    @Override
    public ObservacionDTO findById(Integer id) {
        Optional<Observacion> observacion = observacionRepository.findById(id);
        if (observacion.isPresent()) {
            return observacionMapper.toObservacionDTO(observacion.get());
        }
        throw new ObservacionNotFoundException("No se encontro observacion con el id: " + id);

    }

    @Override
    public ObservacionDTO save(ObservacionDTO observacionDTO) {
        Observacion observacion = observacionMapper.toObservacion(observacionDTO);
        Optional<Problema> problema = problemaRepository.findById(observacionDTO.getProblemaId());
        if (problema.isPresent()) {
            observacion.setProblema(problema.get());
        } else {
            throw new ProblemaNotFoundException("No se encontro problema con el id: " + observacionDTO.getProblemaId());
        }
        observacion.setFecha(new java.util.Date());
        Observacion savedObservacion = observacionRepository.save(observacion);
        return observacionMapper.toObservacionDTO(savedObservacion);
    }

    @Override
    public ObservacionDTO update(ObservacionDTO observacionDTO) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }
    @Override
    public Page<ObservacionDTO> findByProblemaId(Integer problemaId, Pageable pageable) {
        Optional<Problema> problema = problemaRepository.findById(problemaId);
        if (problema.isPresent()) {
            Page<Observacion> listaObservaciones = observacionRepository.findByProblema(problema.get(), pageable);
            if (listaObservaciones.getTotalElements() == 0) {
                throw new ObservacionNotFoundException("no se encontraron observaciones");
            }
            return new PageImpl<>(observacionMapper.toObservacionDTOList(listaObservaciones.getContent()), pageable, listaObservaciones.getTotalElements());
        }
        throw new ProblemaNotFoundException("No se encontro problema con el id: " + problemaId);
    }
}
