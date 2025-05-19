package com.udea.lis.scan.model.repository;

import com.udea.lis.scan.model.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{

    Usuario findByCorreo(String correo);
    Page<Usuario> findByRol(String rol, Pageable pageable);
    Page<Usuario> findAll(Pageable pageable);
}
