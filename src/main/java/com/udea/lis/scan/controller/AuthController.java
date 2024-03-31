package com.udea.lis.scan.controller;

import com.udea.lis.scan.model.dto.UsuarioDTO;
import com.udea.lis.scan.model.dto.auth.LoginUserDto;
import com.udea.lis.scan.model.dto.auth.ResponseAuth;
import com.udea.lis.scan.service.Auth.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService AuthService;

    @PostMapping("/login")
    public ResponseEntity<ResponseAuth> login(@RequestBody LoginUserDto loginUserDto){
        return new ResponseEntity<>(AuthService.login(loginUserDto), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseAuth> register(@RequestBody UsuarioDTO createUserDto){
        return new ResponseEntity<>(AuthService.registro(createUserDto), HttpStatus.CREATED);
    }


}
