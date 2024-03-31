package com.udea.lis.scan.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MainController {

    @GetMapping("/")
    public String Hello(){
        return "Hello World";
    }

    @GetMapping("/test")
    public String HelloTest(){
        return "Hello Test";
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
