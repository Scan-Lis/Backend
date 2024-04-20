package com.udea.lis.scan.service.auth;

import com.udea.lis.scan.model.dto.UsuarioDTO;
import com.udea.lis.scan.model.dto.auth.LoginUserDto;
import com.udea.lis.scan.model.dto.auth.ResponseAuth;
import com.udea.lis.scan.model.entity.Usuario;
import com.udea.lis.scan.model.mapper.UsuarioMapper;
import com.udea.lis.scan.model.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private UsuarioRepository usuarioRepository;
    private UsuarioMapper usuarioMapper;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;

    public ResponseAuth registro(UsuarioDTO createUserDto){
        Usuario usuario = usuarioMapper.toUsuario(createUserDto);
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        Usuario usuarioResponse = usuarioRepository.save(usuario);
        if(usuarioResponse == null){
            throw new RuntimeException("Error al registrar usuario");
        }
        String token = jwtService.createToken(new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()));
        return new ResponseAuth(token);
    }

    public ResponseAuth login(LoginUserDto loginUserDto) throws UsernameNotFoundException, BadCredentialsException {
        try{
            String userName = loginUserDto.getCorreo();
            String password = loginUserDto.getContrasena();
            Authentication authentication = this.authenticate(userName, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = jwtService.createToken(authentication);
            return new ResponseAuth(accessToken);
        }catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Usuario no fue encontrado");
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Contraseña incorrecta");
        }


    }

    public Authentication authenticate(String userName, String password) throws UsernameNotFoundException, BadCredentialsException {
        try {
            UserDetails usuario = this.loadUserByUsername(userName);

            if(!passwordEncoder.matches(password, usuario.getPassword())){
                throw new BadCredentialsException("Contraseña incorrecta");
            }
            return new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        }
        catch (Exception e){
            throw new UsernameNotFoundException("Usuario es encontrado");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String correo = username;
        Usuario usuario =  usuarioRepository.findByCorreo(correo);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return usuario;

        }


}
