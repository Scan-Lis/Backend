package com.udea.lis.scan.model.repository;


import com.udea.lis.scan.model.entity.Computador;
import com.udea.lis.scan.model.entity.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Integer> {

    @Query("SELECT r FROM Reporte r WHERE r.computador.id LIKE %?1%")
    List<Reporte> findReportesBySala(String Sala);

    List<Reporte> findByComputador(Computador computador);

    List<Reporte> findByTipo(String tipo);

    List<Reporte> findByAlmacenado(Boolean almacenado);
}
