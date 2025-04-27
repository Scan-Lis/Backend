package com.udea.lis.scan.service.problemaservice;

import com.udea.lis.scan.error.ProblemaNotFoundException;
import com.udea.lis.scan.error.ReporteNotFoundException;
import com.udea.lis.scan.model.dto.ComputadorDTO;
import com.udea.lis.scan.model.dto.ProblemaDTO;
import com.udea.lis.scan.model.entity.Computador;
import com.udea.lis.scan.model.entity.Problema;
import com.udea.lis.scan.model.entity.Reporte;
import com.udea.lis.scan.model.entity.Usuario;
import com.udea.lis.scan.model.enums.ESala;
import com.udea.lis.scan.model.mapper.ComputadorMapper;
import com.udea.lis.scan.model.mapper.ProblemaMapper;
import com.udea.lis.scan.model.repository.ProblemaRepository;
import com.udea.lis.scan.model.repository.UsuarioRepository;
import com.udea.lis.scan.service.computadorservice.ComputadorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProblemaServiceImpl implements IProblemaService {

    private final ComputadorService computadorService;
    private final ComputadorMapper computadorMapper;
    private ProblemaRepository problemaRepository;
    private ProblemaMapper problemaMapper;
    private UsuarioRepository usuarioRepository;

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
        Problema problema = problemaRepository.findById(id).orElseThrow(() -> new ProblemaNotFoundException("No se encontro problema con el id: " + id));
        return problemaMapper.toProblemaDTO(problema);
    }

    @Override
    public Page<ProblemaDTO> getProblemas(Pageable pageable) {
        Page<Problema> listaProblemas = problemaRepository.findAll(pageable);
        if (listaProblemas.getTotalElements() == 0){
            throw new ProblemaNotFoundException("no se encontraron problemas");
        }
        return new PageImpl<>(problemaMapper.toProblemasDTOList(listaProblemas.getContent()), pageable, listaProblemas.getTotalElements());
    }

    @Override
    public Page<ProblemaDTO> getProblemasByPc(ESala sala, Integer numeroPc, Pageable pageable) {
        ComputadorDTO computadorDTO = computadorService.getComputadorByNumeroPcAndSala(sala.toString(), numeroPc);
        Computador computador = computadorMapper.toComputador(computadorDTO);
        Page<Problema> listaProblemas = problemaRepository.findByComputador(computador, pageable);
        if(listaProblemas.getTotalElements() == 0){
            throw new ProblemaNotFoundException("no se encontraron problemas en el computador: " + sala + "Pc" + numeroPc);
        }
        return new PageImpl<>(problemaMapper.toProblemasDTOList(listaProblemas.getContent()), pageable, listaProblemas.getTotalElements());
    }

    @Override
    public Page<ProblemaDTO> getProblemasBySala(ESala sala, Pageable pageable) {
        Page<Problema> listaProblemas = problemaRepository.findProblemasBySala(sala.toString(), pageable);
        if(listaProblemas.getTotalElements() == 0){
            throw new ProblemaNotFoundException("no se encontraron problemas en la sala: " + sala);
        }
        return new PageImpl<>(problemaMapper.toProblemasDTOList(listaProblemas.getContent()), pageable, listaProblemas.getTotalElements());

    }

    @Override
    public Page<ProblemaDTO> getProblemasBySolucionado(Boolean solucionado, Pageable pageable) {
        Page<Problema> listaProblemas = problemaRepository.findBySolucionado(solucionado, pageable);
        if(listaProblemas.getTotalElements() == 0){
            throw new ProblemaNotFoundException("no se encontraron problemas solucionados: " + solucionado);
        }
        return new PageImpl<>(problemaMapper.toProblemasDTOList(listaProblemas.getContent()), pageable, listaProblemas.getTotalElements());

    }

    @Override
    public Page<ProblemaDTO> getProblemasByAuxiliarAsignado(String usuario, Pageable pageable) {
        return null;
    }

    @Override
    public Page<ProblemaDTO> getProblemasByFechaCreacionBetween(Date fechaInicio, Date fechaFin, Pageable pageable) {
        Page<Problema> listaProblemas = problemaRepository.findByFechaCreacionBetween(fechaInicio, fechaFin, pageable);
        if(listaProblemas.getTotalElements() == 0){
            throw new ProblemaNotFoundException("no se encontraron problemas creados entre las fechas: " + fechaInicio + " y " + fechaFin);
        }
        return new PageImpl<>(problemaMapper.toProblemasDTOList(listaProblemas.getContent()), pageable, listaProblemas.getTotalElements());
    }

    @Override
    public Page<ProblemaDTO> getProblemasByFechaTerminacionBetween(Date fechaInicio, Date fechaFin, Pageable pageable) {
        Page<Problema> listaProblemas = problemaRepository.findByFechaTerminacionBetween(fechaInicio, fechaFin, pageable);
        if(listaProblemas.getTotalElements() == 0){
            throw new ProblemaNotFoundException("no se encontraron problemas terminados entre las fechas: " + fechaInicio + " y " + fechaFin);
        }
        return new PageImpl<>(problemaMapper.toProblemasDTOList(listaProblemas.getContent()), pageable, listaProblemas.getTotalElements());
    }


    @Override
    public void deleteProblema(Integer id) {
        Optional<Problema> problema = problemaRepository.findById(id);
        if (!problema.isPresent()){
            throw new ProblemaNotFoundException("No existe problema con el id: " + id);
        }
        problemaRepository.deleteById(id);
        ComputadorDTO computadorDTO = computadorMapper.toComputadorDTO(problema.get().getComputador());
        computadorService.actualizarEstado(computadorDTO.getSala().toString(), computadorDTO.getNumeroPc());
    }

    @Override
    public ProblemaDTO solucionarProblema(Integer id, Boolean solucionado) {
        Optional<Problema> problema = problemaRepository.findById(id);
        if (problema.isEmpty()){
            throw new ProblemaNotFoundException("No existe problema con el id: " + id);
        }
        Problema problemaSolucionado = problema.get();
        if (problemaSolucionado.getFechaTerminacion() != null){
            throw new ProblemaNotFoundException("El problema ya fue solucionado");
        }if (problemaSolucionado.getAuxiliarAsignado() == null){
            throw new ProblemaNotFoundException("El problema no tiene un auxiliar asignado");
        }
        problemaSolucionado.setSolucionado(solucionado);
        problemaSolucionado.setFechaTerminacion(new Date());
        Problema problemaActualizado = problemaRepository.save(problemaSolucionado);
        ComputadorDTO computadorDTO = computadorMapper.toComputadorDTO(problema.get().getComputador());
        computadorService.actualizarEstado(computadorDTO.getSala().toString(), computadorDTO.getNumeroPc());
        return problemaMapper.toProblemaDTO(problemaActualizado);
    }

    @Override
    public void asignarProblema(Integer id, String correoUsuario) {
        Optional<Problema> problema = problemaRepository.findById(id);
        if (problema.isEmpty()){
            throw new ProblemaNotFoundException("No existe problema con el id: " + id);
        }
        Problema problemaAsignado = problema.get();
        if (problemaAsignado.getAuxiliarAsignado() != null){
            throw new ProblemaNotFoundException("El problema ya tiene un auxiliar asignado");
        }
        Usuario usuario =  usuarioRepository.findByCorreo(correoUsuario);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        problemaAsignado.setAuxiliarAsignado(usuario);
        ComputadorDTO computadorDTO = computadorMapper.toComputadorDTO(problema.get().getComputador());
        computadorService.actualizarEstado(computadorDTO.getSala().toString(), computadorDTO.getNumeroPc());
    }
}
