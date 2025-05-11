package com.udea.lis.scan.model.repository;

import com.udea.lis.scan.model.entity.Computador;
import com.udea.lis.scan.model.entity.Observacion;
import com.udea.lis.scan.model.entity.Problema;
import com.udea.lis.scan.model.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ObservacionRepository extends JpaRepository<Observacion, Integer> {
    Page<Observacion> findByProblema(Problema problema, Pageable pageable);

}
