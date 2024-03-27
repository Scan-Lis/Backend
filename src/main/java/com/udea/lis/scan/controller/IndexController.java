package com.udea.lis.scan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IndexController {

    @GetMapping("/hello")
    public String Hello(){
        return "Hello World";
    }

    @GetMapping("/hello/admin")
    public String HelloAdmin(){
        return "Hello Admin";
    }

    @GetMapping("/hello/auxiliar")
    public String HelloAuxiliar(){
        return "Hello Auxiliar";
    }

}
