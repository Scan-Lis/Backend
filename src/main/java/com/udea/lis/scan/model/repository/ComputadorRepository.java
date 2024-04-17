package com.udea.lis.scan.model.repository;

import com.udea.lis.scan.model.entity.Computador;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ComputadorRepository extends CrudRepository<Computador, String> {
        ArrayList<Computador> findBySala(String sala);


}
