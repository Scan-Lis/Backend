package com.udea.lis.scan.controller;

import com.udea.lis.scan.model.dto.UsuarioDTO;
import com.udea.lis.scan.model.dto.auth.LoginUserDto;
import com.udea.lis.scan.model.dto.auth.ResponseAuth;
import com.udea.lis.scan.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;
    @Operation(summary = "Login", description = "Login para obtener token de autenticación", tags = { "authenticaction" } , responses = {
            @ApiResponse(responseCode = "200", description = "Successful login", content = @Content(schema = @Schema(implementation = ResponseAuth.class ))),
            @ApiResponse(responseCode = "400", description = "Usuario o contraseña incorrectos", content = @Content(schema = @Schema(implementation = String.class))) })
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginUserDto loginUserDto){
        try {
            return new ResponseEntity<>(authService.login(loginUserDto), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Usuario o contraseña incorrectos");
        }
    }

    @Operation(summary = "Registro", description = "Registro de usuario", tags = { "authenticaction" }, responses = {
            @ApiResponse(responseCode = "201", description = "Usuario creado", content = @Content(schema = @Schema(implementation = ResponseAuth.class)) ),
            @ApiResponse(responseCode = "400", description = "Error al registrar usuario", content = @Content(schema = @Schema(implementation = String.class))) })
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UsuarioDTO createUserDto){
        try {
            return new ResponseEntity<>(authService.registro(createUserDto), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al registrar usuario");
        }

    }


}
