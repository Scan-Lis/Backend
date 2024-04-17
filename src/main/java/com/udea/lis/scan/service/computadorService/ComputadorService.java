package com.udea.lis.scan.service.computadorService;

import com.udea.lis.scan.model.dto.ComputadorDTO;
import com.udea.lis.scan.model.entity.Computador;
import com.udea.lis.scan.model.mapper.ComputadorMapper;
import com.udea.lis.scan.model.repository.ComputadorRepository;
import com.udea.lis.scan.service.computadorService.IComputadorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ComputadorService implements IComputadorService {
    private ComputadorRepository computadorRepository;

    private ComputadorMapper computadorMapper;

    @Override
    public Iterable<ComputadorDTO> getComputadoresBySala(String sala) throws RuntimeException{
        List<Computador> computadores = computadorRepository.findBySala(sala);
        if (computadores.isEmpty()){
            throw new RuntimeException("No se encontraron computadores en la sala " + sala);
        }
        return computadorMapper.toComputadoresDTOList(computadores);


    }
    @Override
    public Iterable<ComputadorDTO> getComputadores() throws RuntimeException {

        List<Computador> computadores = (List<Computador>) computadorRepository.findAll();
        if (computadores.isEmpty()){
            throw new RuntimeException("No se encontraron computadores");
        }
        return computadorMapper.toComputadoresDTOList(computadores);
        
    }
    @Override
    public ComputadorDTO saveComputador(ComputadorDTO computadorDTO) throws RuntimeException {
        Computador computador = computadorMapper.toComputador(computadorDTO);
        Computador computadorCreado = computadorRepository.save(computador);
        if (computadorRepository.existsById(computadorCreado.getId())){
            return computadorMapper.toComputadorDTO(computadorCreado);
        }
        throw new RuntimeException("Error al guardar el computador");
    }
     @Override
    public void deleteComputador(String sala, Integer numeroPc) throws RuntimeException {
        String numeroPcString = numeroPc > 9 ? numeroPc.toString() : "0" + numeroPc.toString();
        String id = sala + "Pc" +  numeroPcString;
        if (computadorRepository.existsById(id)) {
            computadorRepository.deleteById(id);
            return;
        }
        throw new RuntimeException("Computador con el id " + id + " no encontrado");

    }

    @Override
    public ComputadorDTO getComputadorByNumeroPcAndSala(String sala, Integer numeroPc) throws RuntimeException {
        String numeroPcString = numeroPc > 9 ? numeroPc.toString() : "0" + numeroPc.toString();
        String id = sala + "Pc" +  numeroPcString;
        Optional<Computador> computador = computadorRepository.findById(id);
        if(computador.isPresent()) {
            return computadorMapper.toComputadorDTO(computador.get());
        }
        throw new RuntimeException("Computador con el id " + id + " no encontrado");

    }


}
