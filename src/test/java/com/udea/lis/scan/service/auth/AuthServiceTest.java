package com.udea.lis.scan.service.auth;

import com.udea.lis.scan.model.dto.UsuarioDTO;
import com.udea.lis.scan.model.dto.auth.LoginUserDto;
import com.udea.lis.scan.model.dto.auth.ResponseAuth;
import com.udea.lis.scan.model.entity.Usuario;
import com.udea.lis.scan.model.mapper.UsuarioMapper;
import com.udea.lis.scan.model.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioMapper usuarioMapper;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private UsuarioDTO createUserDto;
    private Usuario usuario;
    private LoginUserDto loginUserDto;
    @BeforeEach
    void setUp() {
        createUserDto = new UsuarioDTO(1000293157, "Juan", "1000293157", "juan@gmail.com", "123456", "AUXILIAR");
        usuario = new Usuario();
        usuario.setId(createUserDto.getId());
        usuario.setCedula(createUserDto.getCedula());
        usuario.setNombre(createUserDto.getNombre());
        usuario.setCorreo(createUserDto.getCorreo());
        usuario.setContrasena(createUserDto.getContrasena());
        usuario.setRol(createUserDto.getRol());
        loginUserDto = new LoginUserDto();
        loginUserDto.setCorreo(createUserDto.getCorreo());
        loginUserDto.setContrasena(createUserDto.getContrasena());
    }

    @Test
    void registro() {
        // Arrange
        String token = "token";
        when(jwtService.createToken(new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()))).thenReturn(token);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioMapper.toUsuario(createUserDto)).thenReturn(usuario);
        when(passwordEncoder.encode(usuario.getContrasena())).thenReturn(usuario.getContrasena());
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        // Act
        ResponseAuth result = authService.registro(createUserDto);
        // Assert
        assertEquals(new ResponseAuth(token), result);

        //exception
        when(usuarioRepository.save(usuario)).thenReturn(null);
        assertThrows(RuntimeException.class, () -> authService.registro(createUserDto));
    }


    @Test
    void login() {
        // Arrange
        String token = "token";
        when(usuarioRepository.findByCorreo(loginUserDto.getCorreo())).thenReturn(usuario);
        when(passwordEncoder.matches(loginUserDto.getContrasena(), usuario.getContrasena())).thenReturn(true);
        when(jwtService.createToken(new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()))).thenReturn(token);
        // Act
        ResponseAuth result = authService.login(loginUserDto);
        // Assert
        assertEquals(new ResponseAuth("token"), result);
        //exception

        when(passwordEncoder.matches(loginUserDto.getContrasena(), usuario.getContrasena())).thenReturn(false);
        assertThrows(BadCredentialsException.class, () -> authService.login(loginUserDto));

        when(usuarioRepository.findByCorreo(loginUserDto.getCorreo())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> authService.login(loginUserDto));


    }

}