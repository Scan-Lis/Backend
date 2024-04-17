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
    @Mapping(target = "estado", source = "estado", qualifiedByName = "EnumEstadoToString")
    @Mapping(target = "sala", source = "sala", qualifiedByName = "EnumSalaToString")
    Computador toComputador(ComputadorDTO computadorDTO);

    @Mapping(target = "numeroPc", source = "id", qualifiedByName = "construirIdInverso")
    @Mapping(target = "estado", source = "estado", qualifiedByName = "StringToEnumEstado")
    @Mapping(target = "sala", source = "sala", qualifiedByName = "StringToEnumSala")
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
    default Integer construirIdInverso(String id) {
        return Integer.parseInt(id.substring(id.length() - 2));
    }

    @Named("EnumEstadoToString")
    default String EnumEstadoToString(EEstado estado){
        return estado.toString();
    }

    @Named("StringToEnumEstado")
    default EEstado StringToEnumEstado(String estado){
        return EEstado.valueOf(estado);
    }

    @Named("EnumSalaToString")
    default String EnumSalaToString(ESala sala){
        return sala.toString();
    }

    @Named("StringToEnumSala")
    default ESala StringToEnumSala(String sala){
        return ESala.valueOf(sala);
    }


}
