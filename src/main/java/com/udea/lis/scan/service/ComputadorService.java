package com.udea.lis.scan.service;

import com.udea.lis.scan.model.dto.ComputadorDTO;
import com.udea.lis.scan.model.entity.Computador;
import com.udea.lis.scan.model.mapper.ComputadorMapper;
import com.udea.lis.scan.model.repository.ComputadorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ComputadorService {
    private final ComputadorRepository computadorRepository;

    private  final ComputadorMapper computadorMapper;

    public Iterable<ComputadorDTO> getComputadoresBySala(String sala) {
        List<Computador> computadores = computadorRepository.findBySala(sala);
        return computadorMapper.toComputadoresDTOList(computadores);

    }

    public Iterable<ComputadorDTO> getComputadores() {
        List<Computador> computadores = (List<Computador>) computadorRepository.findAll();
        return computadorMapper.toComputadoresDTOList(computadores);
        
    }

    public ComputadorDTO saveComputador(ComputadorDTO computadorDTO) {
        Computador computador = computadorMapper.toComputador(computadorDTO);
        computadorRepository.save(computador);
        return computadorMapper.toComputadorDTO(computador);
    }

    public ComputadorDTO deleteComputador(Integer id) {
        if (computadorRepository.existsById(id)) {
            Optional<Computador> computador = computadorRepository.findById(id);
            computadorRepository.deleteById(id);
        }
        return null;
    }


}
