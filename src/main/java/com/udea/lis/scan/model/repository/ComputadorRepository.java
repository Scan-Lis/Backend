package com.udea.lis.scan.model.repository;

import com.udea.lis.scan.model.entity.Computador;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface ComputadorRepository extends CrudRepository<Computador, Integer> {
        ArrayList<Computador> findBySala(String sala);
}
