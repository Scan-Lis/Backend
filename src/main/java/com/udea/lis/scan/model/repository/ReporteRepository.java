package com.udea.lis.scan.model.repository;


import com.udea.lis.scan.model.entity.Computador;
import com.udea.lis.scan.model.entity.Reporte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Integer> {

    @Query("SELECT r FROM Reporte r WHERE r.computador.id LIKE %?1%")
    Page<Reporte> findReportesBySala(String Sala,  Pageable pageable);

    Page<Reporte> findByComputador(Computador computador, Pageable pageable);

    Page<Reporte> findByTipo(String tipo, Pageable pageable);

    Page<Reporte> findByAlmacenado(Boolean almacenado, Pageable pageable);
}
