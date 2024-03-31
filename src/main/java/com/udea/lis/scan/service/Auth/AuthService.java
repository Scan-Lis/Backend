package com.udea.lis.scan.service.Auth;

import com.udea.lis.scan.model.dto.UsuarioDTO;
import com.udea.lis.scan.model.dto.auth.LoginUserDto;
import com.udea.lis.scan.model.dto.auth.ResponseAuth;
import com.udea.lis.scan.model.entity.Usuario;
import com.udea.lis.scan.model.mapper.UsuarioMapper;
import com.udea.lis.scan.model.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
        usuarioRepository.save(usuario);

        List<SimpleGrantedAuthority> roles = List.of(new SimpleGrantedAuthority("ROLE_"+ usuario.getRol()));

        User usuarioDetails = new User(usuario.getCorreo(), usuario.getContrasena(), roles); // toco cambiarlo por que mis roles son diferentes, son strings

        String token = jwtService.createToken(new UsernamePasswordAuthenticationToken(usuarioDetails.getUsername(), null, usuarioDetails.getAuthorities()));

        return new ResponseAuth(token);
    }

    public ResponseAuth login(LoginUserDto loginUserDto){
        String userName = loginUserDto.getCorreo();
        String password = loginUserDto.getContrasena();

        Authentication authentication = this.authenticate(userName, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtService.createToken(authentication);

        return new ResponseAuth(accessToken);
    }

    public Authentication authenticate(String userName, String password) {
        User usuario = (User) this.loadUserByUsername(userName);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        if(!passwordEncoder.matches(password, usuario.getPassword())){
            throw new BadCredentialsException("Contraseña incorrecta");
        }
        return new UsernamePasswordAuthenticationToken(usuario.getUsername(), password, usuario.getAuthorities());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String correo = username;
        Usuario usuario =  usuarioRepository.findByCorreo(correo);

        List<SimpleGrantedAuthority> roles = List.of(new SimpleGrantedAuthority("ROLE_"+ usuario.getRol()));

        return new User(usuario.getCorreo(), usuario.getContrasena(), roles); // toco cambiarlo por que mis roles son diferentes, son strings

    }


}
