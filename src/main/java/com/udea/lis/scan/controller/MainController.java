package com.udea.lis.scan.controller;

import com.udea.lis.scan.model.dto.ReporteDTO;
import com.udea.lis.scan.model.entity.Computador;
import com.udea.lis.scan.model.entity.Reporte;
import com.udea.lis.scan.model.mapper.ReporteMapper;
import com.udea.lis.scan.model.repository.ComputadorRepository;
import com.udea.lis.scan.model.repository.ReporteRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class MainController {

    ReporteRepository reporteRepository;

    ComputadorRepository computadorRepository;

    ReporteMapper reporteMapper;

    @GetMapping("/")
    public String hello(){
        return "Hello World";
    }

    @GetMapping("/test")
    public String helloTest(){
        return "Hello Test";
    }

    @GetMapping("/admin")
    public String helloAdmin(){
        return "Hello Admin";
    }

    @GetMapping("/auxiliar")
    public String helloAuxiliar(){
        return "Hello Auxiliar";
    }

}
