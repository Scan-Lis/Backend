package com.udea.lis.scan.model.mapper;

import com.udea.lis.scan.model.dto.ComputadorDTO;
import com.udea.lis.scan.model.entity.Computador;
import com.udea.lis.scan.model.enums.EEstado;
import com.udea.lis.scan.model.enums.ESala;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComputadorMapper {
    @Mapping(target = "id", source = "computadorDTO", qualifiedByName = "construirId")
    @Mapping(target = "estado", source = "estado", qualifiedByName = "enumEstadoToString")
    @Mapping(target = "sala", source = "sala", qualifiedByName = "enumSalaToString")
    Computador toComputador(ComputadorDTO computadorDTO);

    @Mapping(target = "numeroPc", source = "computador", qualifiedByName = "construirIdInverso")
    @Mapping(target = "estado", source = "estado", qualifiedByName = "stringToEnumEstado")
    @Mapping(target = "sala", source = "sala", qualifiedByName = "stringToEnumSala")
    ComputadorDTO toComputadorDTO(Computador computador);

    List<ComputadorDTO> toComputadoresDTOList(List<Computador> computadores);

    List<Computador> toComputadoresList(List<ComputadorDTO> computadoresDTO);


    @Named("construirId")
    default String construirId(ComputadorDTO computadorDTO) {
        Integer numeroPc = computadorDTO.getNumeroPc();
        String numeroPcString = numeroPc > 9 ? numeroPc.toString() : "0" + numeroPc.toString();
        return computadorDTO.getSala() + "Pc"+ numeroPcString;
    }
    @Named("construirIdInverso")
    default Integer construirIdInverso(Computador computador) {
        String id = computador.getId();
        return Integer.parseInt(id.substring(id.length() - 2));
    }

    @Named("enumEstadoToString")
    default String enumEstadoToString(EEstado estado){
        return estado.toString();
    }

    @Named("stringToEnumEstado")
    default EEstado stringToEnumEstado(String estado){
        return EEstado.valueOf(estado);
    }

    @Named("enumSalaToString")
    default String enumSalaToString(ESala sala){
        return sala.toString();
    }

    @Named("stringToEnumSala")
    default ESala stringToEnumSala(String sala){
        return ESala.valueOf(sala);
    }


}
