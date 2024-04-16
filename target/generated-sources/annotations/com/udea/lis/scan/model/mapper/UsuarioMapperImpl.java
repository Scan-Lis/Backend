package com.udea.lis.scan.model.mapper;

import com.udea.lis.scan.model.dto.UsuarioDTO;
import com.udea.lis.scan.model.entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-16T15:02:32-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Amazon.com Inc.)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toUsuario(UsuarioDTO usuarioDTO) {
        if ( usuarioDTO == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setId( usuarioDTO.getId() );
        usuario.setNombre( usuarioDTO.getNombre() );
        usuario.setCedula( usuarioDTO.getCedula() );
        usuario.setCorreo( usuarioDTO.getCorreo() );
        usuario.setContrasena( usuarioDTO.getContrasena() );
        usuario.setRol( usuarioDTO.getRol() );

        return usuario;
    }

    @Override
    public UsuarioDTO toUsuarioDTO(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setId( usuario.getId() );
        usuarioDTO.setNombre( usuario.getNombre() );
        usuarioDTO.setCedula( usuario.getCedula() );
        usuarioDTO.setCorreo( usuario.getCorreo() );
        usuarioDTO.setContrasena( usuario.getContrasena() );
        usuarioDTO.setRol( usuario.getRol() );

        return usuarioDTO;
    }

    @Override
    public List<UsuarioDTO> toUsuariosDTOList(List<Usuario> usuarios) {
        if ( usuarios == null ) {
            return null;
        }

        List<UsuarioDTO> list = new ArrayList<UsuarioDTO>( usuarios.size() );
        for ( Usuario usuario : usuarios ) {
            list.add( toUsuarioDTO( usuario ) );
        }

        return list;
    }

    @Override
    public List<Usuario> toUsuariosList(List<UsuarioDTO> usuariosDTO) {
        if ( usuariosDTO == null ) {
            return null;
        }

        List<Usuario> list = new ArrayList<Usuario>( usuariosDTO.size() );
        for ( UsuarioDTO usuarioDTO : usuariosDTO ) {
            list.add( toUsuario( usuarioDTO ) );
        }

        return list;
    }
}
