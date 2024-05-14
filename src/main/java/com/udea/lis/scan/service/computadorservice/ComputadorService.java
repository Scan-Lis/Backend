package com.udea.lis.scan.service.computadorservice;
import com.udea.lis.scan.error.ComputadorNotFoundException;
import com.udea.lis.scan.error.ComputadorOperationException;
import com.udea.lis.scan.model.dto.ComputadorDTO;
import com.udea.lis.scan.model.dto.ReporteDTO;
import com.udea.lis.scan.model.entity.Computador;
import com.udea.lis.scan.model.entity.Reporte;
import com.udea.lis.scan.model.enums.EEstado;
import com.udea.lis.scan.model.mapper.ComputadorMapper;
import com.udea.lis.scan.model.repository.ComputadorRepository;
import com.udea.lis.scan.service.reporteservice.ReporteServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ComputadorService implements IComputadorService {
    private ComputadorRepository computadorRepository;

    private ComputadorMapper computadorMapper;


    @Override
    public Page<ComputadorDTO> getComputadoresBySala(String sala, Pageable pageable) throws ComputadorNotFoundException{
        Page<Computador> computadores = computadorRepository.findBySala(sala, pageable);
        if (computadores.getTotalElements() == 0){ // si definitivamente no hay computadores
            throw new ComputadorNotFoundException("No se encontraron computadores en la sala " + sala);
        }
        return new PageImpl<>(computadorMapper.toComputadoresDTOList(computadores.getContent()), pageable, computadores.getTotalElements());
    }
    @Override
    public Page<ComputadorDTO> getComputadores( Pageable pageable) throws ComputadorNotFoundException {
        Page<Computador> computadores = computadorRepository.findAll(pageable);
        if (computadores.getTotalElements() == 0){ // si definitivamente no hay computadores
            throw new ComputadorNotFoundException("No se encontraron computadores");
        }
        return new PageImpl<>(computadorMapper.toComputadoresDTOList(computadores.getContent()), pageable, computadores.getTotalElements());
    }
    @Override
    public ComputadorDTO saveComputador(ComputadorDTO computadorDTO) throws ComputadorOperationException {
        Computador computador = computadorMapper.toComputador(computadorDTO);
        Computador computadorCreado = computadorRepository.save(computador);
        if (computadorRepository.existsById(computadorCreado.getId())){
            return computadorMapper.toComputadorDTO(computadorCreado);
        }
        throw new ComputadorOperationException("Error al guardar el computador");
    }
     @Override
    public void deleteComputador(String sala, Integer numeroPc) throws ComputadorNotFoundException {
        String numeroPcString = numeroPc > 9 ? numeroPc.toString() : "0" + numeroPc.toString();
        String id = sala + "Pc" +  numeroPcString;
        if (computadorRepository.existsById(id)) {
            computadorRepository.deleteById(id);
            return;
        }
        throw new ComputadorNotFoundException("Computador con el id " + id + " no encontrado");

    }

    @Override
    public ComputadorDTO getComputadorByNumeroPcAndSala(String sala, Integer numeroPc) throws ComputadorNotFoundException {
        String numeroPcString = numeroPc > 9 ? numeroPc.toString() : "0" + numeroPc.toString();
        String id = sala + "Pc" +  numeroPcString;
        Optional<Computador> computador = computadorRepository.findById(id);
        if(computador.isPresent()) {
            return computadorMapper.toComputadorDTO(computador.get());
        }
        throw new ComputadorNotFoundException("Computador con el id " + id + " no encontrado");
    }

    @Override
    public void actualizarEstado(String sala, Integer numeroPc) {
        String numeroPcString = numeroPc > 9 ? numeroPc.toString() : "0" + numeroPc.toString();
        String id = sala + "Pc" +  numeroPcString;
        Optional<Computador> computadorResult = computadorRepository.findById(id);
        if(!computadorResult.isPresent()) {
            return;
        }
        Computador computador = computadorResult.get();
        List<Reporte> reportesDelPc = computador.getReportes();


        if(this.estaEnMantenimiento(false)) { //cambiar mas adelante
            computador.setEstado(EEstado.Mantenimiento.toString());
        } else if(this.estaFallando(false)) { // cambiar  mas adelante
            computador.setEstado(EEstado.Fallando.toString());
        } else if(this.estaEnAlerta(reportesDelPc)) {
            computador.setEstado(EEstado.Alerta.toString());
        } else {
            computador.setEstado(EEstado.Funcionando.toString());
        }

        computadorRepository.save(computador);
    }

    private boolean estaEnAlerta(List<Reporte> reportesDelPc) {
        if(reportesDelPc.isEmpty()) {
            return false;
        }
        for (Reporte reporte : reportesDelPc) {
            if (reporte.getAlmacenado() == false) {
                return true;
            }
        }
        return false;
    }

    private boolean estaFallando(Boolean parametro) { // de momento no se usa
        return parametro;
    }

    private boolean estaEnMantenimiento( Boolean parametro) { // de momento no se usa
        return parametro;
    }

}
