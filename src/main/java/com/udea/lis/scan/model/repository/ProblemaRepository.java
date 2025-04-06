package com.udea.lis.scan.model.repository;

import com.udea.lis.scan.model.entity.Computador;
import com.udea.lis.scan.model.entity.Problema;
import com.udea.lis.scan.model.entity.Reporte;
import com.udea.lis.scan.model.entity.Usuario;
import org.hibernate.boot.model.relational.AuxiliaryDatabaseObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ProblemaRepository extends JpaRepository<Problema, Integer> {
    Page<Problema> findProblemasByAuxiliarAsignado(Usuario usuario, Pageable pageable);
    Page<Problema> findProblemasBySolucionado(Boolean solucionado, Pageable pageable);

    Page<Problema> findProblemasByComputador(Computador computador, Pageable pageable);

    Page<Problema> findByFechaCreacionBetween(Date fechaInicio, Date fechaFin, Pageable pageable);

    Page<Problema> findByFechaTerminacionBetween(Date fechaInicio, Date fechaFin, Pageable pageable);

}
