package com.udea.lis.scan.service.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.udea.lis.scan.model.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class JwtServiceTest {


    @InjectMocks
    private JwtService jwtService;

    Usuario usuario = new Usuario();

    @BeforeEach
    void setUp() {
        jwtService.setSecretKey("testSecretKey");
        jwtService.setTimeExpiration("3600000"); // Tiempo de expiración en milisegundos

        usuario.setNombre("testUser");
        usuario.setContrasena("testPassword");
        usuario.setId(15);
        usuario.setCorreo("testUser@email.com");
        usuario.setCedula("1234567890");
        usuario.setRol("ADMIN");


    }

    @Test
    void createToken() {
        // Arrange
        Authentication auth = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        // Act
        String token = jwtService.createToken(auth);
        DecodedJWT decodedJWT = jwtService.validateToken(token);// Assert
        assertNotNull(token);
        assertEquals("testUser", jwtService.getUsername(decodedJWT));
        assertEquals("\"ROLE_ADMIN\"",jwtService.getRol(decodedJWT));
        assertEquals("testUser", jwtService.getClaim(decodedJWT, "nombre").asString());
        assertNotNull(jwtService.getClaims(decodedJWT));
    }

}