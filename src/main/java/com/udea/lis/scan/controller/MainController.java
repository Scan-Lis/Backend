package com.udea.lis.scan.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@AllArgsConstructor
public class IndexController {

    @GetMapping()
    public String Hello(){
        return "Hello World";
    }

    @GetMapping("/admin")
    public String HelloAdmin(){
        return "Hello Admin";
    }

    @GetMapping("/auxiliar")
    public String HelloAuxiliar(){
        return "Hello Auxiliar";
    }

}
