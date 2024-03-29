package com.udea.lis.scan.mapper;

import com.udea.lis.scan.dto.UsuarioDTO;
import com.udea.lis.scan.model.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "cedula", source = "cedula")
    @Mapping(target = "correo", source = "correo")
    @Mapping(target = "contrasena", source = "contrasena")
    @Mapping(target = "rol", source = "rol")
    Usuario toUsuario(UsuarioDTO usuarioDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "cedula", source = "cedula")
    @Mapping(target = "correo", source = "correo")
    @Mapping(target = "contrasena", source = "contrasena")
    @Mapping(target = "rol", source = "rol")
    UsuarioDTO toUsuarioDTO(Usuario usuario);


}
