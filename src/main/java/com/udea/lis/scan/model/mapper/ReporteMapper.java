package com.udea.lis.scan.model.mapper;

import com.udea.lis.scan.model.dto.ComputadorDTO;
import com.udea.lis.scan.model.dto.ReporteDTO;
import com.udea.lis.scan.model.entity.Computador;
import com.udea.lis.scan.model.entity.Reporte;
import com.udea.lis.scan.model.enums.EReporte;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ReporteMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "correo", source = "correo")
    @Mapping(target = "tipo", source = "tipo", qualifiedByName = "toEReporte")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "fecha", source = "fecha")
    @Mapping(target = "almacenado", source = "almacenado")
    @Mapping(target = "sala" , source = "reporte", qualifiedByName = "extractSala")
    @Mapping(target = "numeroPc", source = "reporte", qualifiedByName = "extractNumeroPc")
    ReporteDTO toReporteDTO(Reporte reporte);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "correo", source = "correo")
    @Mapping(target = "tipo", source = "tipo", qualifiedByName = "ereporteToString")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "almacenado", source = "almacenado")
    @Mapping(target = "fecha", source = "fecha")
    Reporte toReporte(ReporteDTO reporteDTO);

    List<ReporteDTO> toReportesDTOList(List<Reporte> reportes);

    List<Reporte> toReportesList(List<ReporteDTO> reportesDTO);

    @Named("toEReporte")
    default EReporte toEReporte(String tipo){
        return EReporte.valueOf(tipo);
    }

    @Named("ereporteToString")
    default String ereporteToString(EReporte tipo){
        return tipo.toString();
    }

    @Named("extractSala")
    default String extractSala(Reporte reporte){
        return reporte.getComputador().getSala().toString();
    }

    @Named("extractNumeroPc")
    default Integer extractNumeroPc(Reporte reporte){
        String id = reporte.getComputador().getId();
        return Integer.parseInt(id.substring(id.length() - 2));
    }

}
