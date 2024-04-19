package com.udea.lis.scan.model.mapper;

import com.udea.lis.scan.model.dto.ComputadorDTO;
import com.udea.lis.scan.model.entity.Computador;
import com.udea.lis.scan.model.enums.EEstado;
import com.udea.lis.scan.model.enums.ESala;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-19T01:31:19-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Amazon.com Inc.)"
)
@Component
public class ComputadorMapperImpl implements ComputadorMapper {

    @Override
    public Computador toComputador(ComputadorDTO computadorDTO) {
        if ( computadorDTO == null ) {
            return null;
        }

        Computador computador = new Computador();

        computador.setId( construirId( computadorDTO ) );
        computador.setEstado( EnumEstadoToString( computadorDTO.getEstado() ) );
        computador.setSala( EnumSalaToString( computadorDTO.getSala() ) );

        return computador;
    }

    @Override
    public ComputadorDTO toComputadorDTO(Computador computador) {
        if ( computador == null ) {
            return null;
        }

        Integer numeroPc = null;
        EEstado estado = null;
        ESala sala = null;

        numeroPc = construirIdInverso( computador.getId() );
        estado = StringToEnumEstado( computador.getEstado() );
        sala = StringToEnumSala( computador.getSala() );

        ComputadorDTO computadorDTO = new ComputadorDTO( numeroPc, estado, sala );

        return computadorDTO;
    }

    @Override
    public List<ComputadorDTO> toComputadoresDTOList(List<Computador> computadores) {
        if ( computadores == null ) {
            return null;
        }

        List<ComputadorDTO> list = new ArrayList<ComputadorDTO>( computadores.size() );
        for ( Computador computador : computadores ) {
            list.add( toComputadorDTO( computador ) );
        }

        return list;
    }

    @Override
    public List<Computador> toComputadoresList(List<ComputadorDTO> computadoresDTO) {
        if ( computadoresDTO == null ) {
            return null;
        }

        List<Computador> list = new ArrayList<Computador>( computadoresDTO.size() );
        for ( ComputadorDTO computadorDTO : computadoresDTO ) {
            list.add( toComputador( computadorDTO ) );
        }

        return list;
    }
}
