package com.udea.lis.scan.service.observacionservice;


import com.udea.lis.scan.model.dto.ObservacionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IObservacionService {
    Page<ObservacionDTO> findAll(Pageable pageable);
    ObservacionDTO findById(Integer id);
    ObservacionDTO save(ObservacionDTO observacionDTO);
    ObservacionDTO update(ObservacionDTO observacionDTO);
    void deleteById(Integer id);
    Page<ObservacionDTO> findByProblemaId(Integer problemaId, Pageable pageable);
}
