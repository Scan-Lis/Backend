package com.udea.lis.scan.model.repository;

import com.udea.lis.scan.model.entity.Computador;
import com.udea.lis.scan.model.entity.Problema;
import com.udea.lis.scan.model.entity.Reporte;
import com.udea.lis.scan.model.entity.Usuario;
import org.hibernate.boot.model.relational.AuxiliaryDatabaseObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ProblemaRepository extends JpaRepository<Problema, Integer> {
    Page<Problema> findByAuxiliarAsignado(Usuario usuario, Pageable pageable);
    Page<Problema> findBySolucionado(Boolean solucionado, Pageable pageable);

    Page<Problema> findByComputador(Computador computador, Pageable pageable);

    @Query("SELECT p FROM Problema p WHERE p.computador.id LIKE %?1%")
    Page<Problema> findProblemasBySala(String sala, Pageable pageable);

    @Query("SELECT p FROM Problema p WHERE p.fechaCreacion BETWEEN :inicio AND :fin")
    Page<Problema> findByFechaCreacionBetween(
            @Param("inicio") Date inicio,
            @Param("fin") Date fin, Pageable pageable);

    @Query("SELECT p FROM Problema p WHERE p.fechaTerminacion BETWEEN :inicio AND :fin")
    Page<Problema> findByFechaTerminacionBetween(
            @Param("inicio") Date inicio,
            @Param("fin") Date fin, Pageable pageable);


}
