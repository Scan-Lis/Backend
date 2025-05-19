package com.udea.lis.scan.service.userservice;

import com.udea.lis.scan.model.dto.ReporteDTO;
import com.udea.lis.scan.model.dto.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    public UsuarioDTO updateName(String email , String newName);

    public UsuarioDTO updateEmail(String email, String newEmail);

    public UsuarioDTO changeRole(String email, String newRole);

    public Boolean deleteUser(String email);

    public UsuarioDTO getUserByEmail(String username);

    public Page<UsuarioDTO> getAllUsers(Pageable pageable);

    public Page<UsuarioDTO> getUsersByRole( String role, Pageable pageable);
}
