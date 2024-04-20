package com.udea.lis.scan.service.computadorservice;

import com.udea.lis.scan.model.dto.ComputadorDTO;

public interface IComputadorService {
    public Iterable<ComputadorDTO> getComputadoresBySala(String sala);

    public Iterable<ComputadorDTO> getComputadores();

    public ComputadorDTO saveComputador(ComputadorDTO computadorDTO);

    public void deleteComputador(String sala, Integer numeroPc);

    public ComputadorDTO getComputadorByNumeroPcAndSala(String sala, Integer numeroPc);


}
