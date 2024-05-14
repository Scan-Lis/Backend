package com.udea.lis.scan.model.repository;

import com.udea.lis.scan.model.entity.Computador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputadorRepository extends JpaRepository<Computador, String> {
        Page<Computador> findBySala(String sala, Pageable pageable);

}
