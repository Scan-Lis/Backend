package com.udea.lis.scan.model.mapper;

import com.udea.lis.scan.model.dto.ProblemaDTO;
import com.udea.lis.scan.model.dto.ReporteDTO;
import com.udea.lis.scan.model.entity.Computador;
import com.udea.lis.scan.model.entity.Problema;
import com.udea.lis.scan.model.entity.Reporte;
import com.udea.lis.scan.model.entity.Usuario;
import com.udea.lis.scan.model.enums.EReporte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProblemaMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descripcionBase", source = "descripcionBase")
    @Mapping(target = "solucionado", source = "solucionado")
    @Mapping(target = "fechaCreacion", source = "fechaCreacion")
    @Mapping(target = "fechaTerminacion", source = "fechaTerminacion")
    @Mapping(target = "auxiliarAsignado", source = "auxiliarAsignado", qualifiedByName = "usuarioToCorreo")
    @Mapping(target = "sala" , source = "problema", qualifiedByName = "extractSala")
    @Mapping(target = "numeroPc", source = "problema", qualifiedByName = "extractNumeroPc")
    ProblemaDTO toProblemaDTO(Problema problema);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "descripcionBase", source = "descripcionBase")
    @Mapping(target = "solucionado", source = "solucionado")
    @Mapping(target = "fechaCreacion", source = "fechaCreacion")
    @Mapping(target = "fechaTerminacion", source = "fechaTerminacion")
    @Mapping(target = "auxiliarAsignado", source = "auxiliarAsignado", qualifiedByName = "correoToUsuario")
    Problema toProblema(ProblemaDTO problemaDTO);

    List<ProblemaDTO> toProblemasDTOList(List<Problema> problemas);
    List<Problema> toProblemasList(List<ProblemaDTO> problemaDTOs);


    @Named("extractSala")
    default String extractSala(Problema problema){
        return problema.getComputador().getSala().toString();
    }

    @Named("extractNumeroPc")
    default Integer extractNumeroPc(Problema problema){
        String id = problema.getComputador().getId();
        return Integer.parseInt(id.substring(id.length() - 2));
    }

    @Named("usuarioToCorreo")
    default String usuarioToCorreo(Usuario auxiliarAsignado) {
        return auxiliarAsignado != null ? auxiliarAsignado.getCorreo() : null;
    }

    @Named("correoToUsuario")
    default Usuario correoToUsuario(String correo) {
        if (correo == null) return null;
        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        return usuario;
    }

}
