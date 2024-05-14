package com.udea.lis.scan.service.computadorservice;

import com.udea.lis.scan.model.dto.ComputadorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IComputadorService {
    public Page<ComputadorDTO> getComputadoresBySala(String sala, Pageable pageable);

    public Page<ComputadorDTO> getComputadores(Pageable pageable);

    public ComputadorDTO saveComputador(ComputadorDTO computadorDTO);

    public void deleteComputador(String sala, Integer numeroPc);

    public ComputadorDTO getComputadorByNumeroPcAndSala(String sala, Integer numeroPc);

    public void actualizarEstado(String sala, Integer numeroPc);


}
