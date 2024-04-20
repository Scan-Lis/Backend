package com.udea.lis.scan.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MainController {

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
