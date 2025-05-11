package com.udea.lis.scan.model.mapper;

import com.udea.lis.scan.model.dto.ObservacionDTO;
import com.udea.lis.scan.model.entity.Observacion;
import com.udea.lis.scan.model.entity.Problema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ObservacionMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "fecha", source = "fecha")
    @Mapping(target = "autor", source = "autor")
    @Mapping(target = "problemaId", source = "problema.id")
    ObservacionDTO toObservacionDTO(Observacion observacion);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "fecha", source = "fecha")
    @Mapping(target = "autor", source = "autor")
    @Mapping(target = "problema", source = "problemaId", qualifiedByName = "problemaIdToProblema")
    Observacion toObservacion(ObservacionDTO observacionDTO);

    List<ObservacionDTO> toObservacionDTOList(List<Observacion> problemas);
    List<Observacion> toObservacionList(List<ObservacionDTO> problemaDTOs);

    @Named("problemaIdToProblema")
    default Problema problemaIdToProblema(Integer problemaId) {
        return null;
    }

}
