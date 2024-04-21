package com.udea.lis.scan.service.reporteservice;

import com.udea.lis.scan.error.ComputadorNotFoundException;
import com.udea.lis.scan.error.ReporteNotFoundException;
import com.udea.lis.scan.error.ReporteOperationException;
import com.udea.lis.scan.model.dto.ComputadorDTO;
import com.udea.lis.scan.model.dto.ReporteDTO;
import com.udea.lis.scan.model.entity.Computador;
import com.udea.lis.scan.model.entity.Reporte;
import com.udea.lis.scan.model.enums.EReporte;
import com.udea.lis.scan.model.enums.ESala;
import com.udea.lis.scan.model.mapper.ComputadorMapper;
import com.udea.lis.scan.model.mapper.ReporteMapper;
import com.udea.lis.scan.model.repository.ReporteRepository;
import com.udea.lis.scan.service.computadorservice.ComputadorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReporteServiceImpl implements IReporteService{

    private ComputadorService computadorService;
    private ReporteRepository reporteRepository;
    private ReporteMapper reporteMapper;
    private ComputadorMapper computadorMapper;

    @Override
    public ReporteDTO getReporte(Integer id) throws ReporteNotFoundException {
        Optional<Reporte> reporte = reporteRepository.findById(id);
        if (reporte.isPresent()){
            return reporteMapper.toReporteDTO(reporte.get());
        }
        throw new ReporteNotFoundException("No se encontro reporte con el id: " + id);
    }

    @Override
    public List<ReporteDTO> getReportes() throws ReporteNotFoundException {
        List<Reporte> listaReportes = (List<Reporte>) reporteRepository.findAll();
        if (listaReportes.isEmpty()){
            throw new ReporteNotFoundException("no se encontraron reportes");
        }
        return reporteMapper.toReportesDTOList(listaReportes);
    }

    @Override
    public List<ReporteDTO> getReportesByPc(ESala sala, Integer numeroPc) throws ComputadorNotFoundException, ReporteNotFoundException {
        ComputadorDTO computadorDTO = computadorService.getComputadorByNumeroPcAndSala(sala.toString(), numeroPc);
        Computador computador = computadorMapper.toComputador(computadorDTO);
        List<Reporte> listaReportes = reporteRepository.findByComputador(computador);
        if(listaReportes.isEmpty()){
            throw new ReporteNotFoundException("No se encontraron reportes en el computador: " + sala + "Pc" + numeroPc);
        }
        return reporteMapper.toReportesDTOList(listaReportes);
    }

    @Override
    public List<ReporteDTO> getReportesBySala(ESala sala) throws ReporteNotFoundException {
        List<Reporte> listaReportes = reporteRepository.findReportesBySala(sala.toString());
        if(listaReportes.isEmpty()){
            throw new ReporteNotFoundException("No se encontraron reportes en la sala: " + sala);
        }
        return reporteMapper.toReportesDTOList(listaReportes);
    }

    @Override
    public List<ReporteDTO> getReportesByTipo(EReporte tipo) throws ReporteNotFoundException {
        List<Reporte> listaReportes = reporteRepository.findByTipo(tipo.toString());
        if(listaReportes.isEmpty()){
            throw new ReporteNotFoundException("No se encontraron reportes de tipo: " + tipo);
        }
        return reporteMapper.toReportesDTOList(listaReportes);
    }

    @Override
    public List<ReporteDTO> getReporteByAlmacenado(Boolean almacenado) throws ReporteNotFoundException {
        List<Reporte> listaReportes = reporteRepository.findByAlmacenado(almacenado);
        if(listaReportes.isEmpty()){
            throw new ReporteNotFoundException(almacenado? "No se encontraron reportes almacenados" : "No se encontraron reportes no almacenados");
        }
        return reporteMapper.toReportesDTOList(listaReportes);
    }

    @Override
    public ReporteDTO saveReporte(ReporteDTO reporteDTO) throws ComputadorNotFoundException, ReporteOperationException{
        Reporte reporte = reporteMapper.toReporte(reporteDTO);
        ComputadorDTO computadorDTO = computadorService.getComputadorByNumeroPcAndSala(reporteDTO.getSala().toString(), reporteDTO.getNumeroPc());
        Computador computador = computadorMapper.toComputador(computadorDTO);
        reporte.setComputador(computador);
        Reporte reporteCreado = reporteRepository.save(reporte);
        if (reporteRepository.existsById(reporteCreado.getId())){
            return reporteMapper.toReporteDTO(reporteCreado);
        }
        throw new ReporteOperationException("Error al guardar el reporte");
    }

    @Override
    public void deleteReporte(Integer id) throws ReporteNotFoundException{
        if (!reporteRepository.existsById(id)){
            throw new ReporteNotFoundException("No existe reporte con el id: " + id);
        }
        reporteRepository.deleteById(id);
    }

    @Override
    public ReporteDTO almacenarReporte(Integer id, Boolean almacenado) throws ReporteNotFoundException{
        Optional<Reporte> reporte = reporteRepository.findById(id);
        if (!reporte.isPresent()){
            throw new ReporteNotFoundException("No se encontro reporte con el id: " + id);
        }
        Reporte reporteActualizado = reporte.get();
        reporteActualizado.setAlmacenado(almacenado);
        reporteActualizado = reporteRepository.save(reporteActualizado);
        return reporteMapper.toReporteDTO(reporteActualizado);
    }


}
