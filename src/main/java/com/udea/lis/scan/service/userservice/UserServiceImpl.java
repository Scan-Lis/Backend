package com.udea.lis.scan.service.userservice;


import com.udea.lis.scan.error.UserNotFoundException;
import com.udea.lis.scan.model.dto.UsuarioDTO;
import com.udea.lis.scan.model.entity.Usuario;
import com.udea.lis.scan.model.mapper.UsuarioMapper;
import com.udea.lis.scan.model.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {


    private UsuarioRepository usuarioRepository;
    private UsuarioMapper usuarioMapper;

    @Override
    public UsuarioDTO updateName(String email, String newName) {
        Usuario usuario = usuarioRepository.findByCorreo(email);
        if(usuario == null){
            throw new UserNotFoundException("Usuario con correo " + email + " no encontrado");
        }
        usuario.setNombre(newName);
        usuario = usuarioRepository.save(usuario);
        usuario.setContrasena(null);
        return usuarioMapper.toUsuarioDTO(usuario);
    }

    @Override
    public UsuarioDTO updateEmail(String email, String newEmail) {
        Usuario usuario = usuarioRepository.findByCorreo(email);
        if(usuario == null){
            throw new UserNotFoundException("Usuario con correo " + email + " no encontrado");
        }
        usuario.setCorreo(newEmail);
        usuario = usuarioRepository.save(usuario);
        usuario.setContrasena(null);
        return usuarioMapper.toUsuarioDTO(usuario);
    }

    @Override
    public UsuarioDTO changeRole(String email, String newRole) {
        Usuario usuario = usuarioRepository.findByCorreo(email);
        if(usuario == null){
            throw new UserNotFoundException("Usuario con correo " + email + " no encontrado");
        }
        usuario.setRol(newRole);
        usuario = usuarioRepository.save(usuario);
        usuario.setContrasena(null);
        return usuarioMapper.toUsuarioDTO(usuario);
    }

    @Override
    public Boolean deleteUser(String email) {
        Usuario usuario = usuarioRepository.findByCorreo(email);
        if(usuario == null){
            throw new UserNotFoundException("Usuario con correo " + email + " no encontrado");
        }
        usuarioRepository.delete(usuario);
        return true;
    }

    @Override
    public UsuarioDTO getUserByEmail(String username) {
        Usuario usuario = usuarioRepository.findByCorreo(username);
        if(usuario == null){
            throw new UserNotFoundException("Usuario con correo " + username + " no encontrado");
        }
        usuario.setContrasena(null);
        return usuarioMapper.toUsuarioDTO(usuario);
    }

    @Override
    public Page<UsuarioDTO> getAllUsers(Pageable pageable) {
        Page<Usuario> usuariosPage = usuarioRepository.findAll(pageable);
        return getUsuarioDTOS(pageable, usuariosPage);
    }

    @Override
    public Page<UsuarioDTO> getUsersByRole(String role, Pageable pageable) {
        Page<Usuario> usuariosPage = usuarioRepository.findByRol(role, pageable);
        return getUsuarioDTOS(pageable, usuariosPage);
    }

    private Page<UsuarioDTO> getUsuarioDTOS(Pageable pageable, Page<Usuario> usuariosPage) {
        if(usuariosPage.getTotalElements() > 0){
            List<UsuarioDTO> usuariosDTO = usuarioMapper.toUsuariosDTOList(usuariosPage.getContent());
            for (UsuarioDTO usuarioDTO : usuariosDTO) {
                usuarioDTO.setContrasena(null);
            }
            return new PageImpl<>(usuariosDTO, pageable, usuariosPage.getTotalElements());
        }
        return null;
    }
}
