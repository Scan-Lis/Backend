package com.udea.lis.scan.model.repository;

import com.udea.lis.scan.model.entity.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{

    Usuario findByCorreo(String correo);
}
