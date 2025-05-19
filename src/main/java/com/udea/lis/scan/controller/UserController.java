package com.udea.lis.scan.controller;

import com.udea.lis.scan.error.UserNotFoundException;
import com.udea.lis.scan.model.dto.UsuarioDTO;
import com.udea.lis.scan.service.userservice.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

     private final IUserService userService;

     @GetMapping("/all")
     @Operation(summary = "Obtener todos los usuarios", description = "Obtener una lista de todos los usuarios", responses = {
             @ApiResponse(responseCode = "200", description = "lista de usuarios")})
     public ResponseEntity<Page<UsuarioDTO>> getAllUsers(Pageable pageable) {
         return ResponseEntity.ok(userService.getAllUsers(pageable));
     }

     @GetMapping("/role/{role}")
        @Operation(summary = "Obtener usuarios por rol", description = "Obtener una lista de usuarios por rol", responses = {
                @ApiResponse(responseCode = "200", description = "lista de usuarios")})
     public ResponseEntity<Page<UsuarioDTO>> getUsersByRole(String role, Pageable pageable) {
         return ResponseEntity.ok(userService.getUsersByRole(role, pageable));
     }

    @Operation(summary = "Obtener usuario por correo", description = "Obtener un usuario por correo", responses = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(schema = @Schema(implementation = String.class))) })
    @GetMapping("/email/{email}")
    public ResponseEntity<Object> getUserByEmail(String email) {
        try {
            return ResponseEntity.ok(userService.getUserByEmail(email));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar usuario por correo", description = "Eliminar un usuario por correo", responses = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(schema = @Schema(implementation = String.class))) })
    @DeleteMapping("/email/{email}")
    public ResponseEntity<Object> deleteUser(String email) {
        try {
            userService.deleteUser(email);
            return ResponseEntity.ok("Usuario eliminado");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar nombre de usuario", description = "Actualizar el nombre de un usuario por correo", responses = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado", content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(schema = @Schema(implementation = String.class))) })
    @PutMapping("/email/{email}/name/{newName}")
    public ResponseEntity<Object> updateName(String email, String newName) {
        try {
            return ResponseEntity.ok(userService.updateName(email, newName));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar correo de usuario", description = "Actualizar el correo de un usuario por correo", responses = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado", content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(schema = @Schema(implementation = String.class))) })
    @PutMapping("/email/{email}/newEmail/{newEmail}")
    public ResponseEntity<Object> updateEmail(String email, String newEmail) {
        try {
            return ResponseEntity.ok(userService.updateEmail(email, newEmail));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar rol de usuario", description = "Actualizar el rol de un usuario por correo", responses = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado", content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(schema = @Schema(implementation = String.class))) })
    @PutMapping("/email/{email}/role/{newRole}")
    public ResponseEntity<Object> updateRole(String email, String newRole) {
        try {
            return ResponseEntity.ok(userService.changeRole(email, newRole));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



}
