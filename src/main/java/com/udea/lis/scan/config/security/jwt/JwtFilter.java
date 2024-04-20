package com.udea.lis.scan.config.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.udea.lis.scan.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtService jwtService;

    public JwtFilter(JwtService jwtService){
        this.jwtService = jwtService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = this.getTokenFormRequest(request);

        if(token == null){
            filterChain.doFilter(request, response);
            return;
        }

        DecodedJWT decodedJWT = jwtService.validateToken(token);

        String username = jwtService.getUsername(decodedJWT);
        String rolString = decodedJWT.getClaim("rol").asString();
        GrantedAuthority rol  = new SimpleGrantedAuthority(rolString);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(username, null, List.of(rol)));
        SecurityContextHolder.setContext(context);



        filterChain.doFilter(request, response);
    }

    private String getTokenFormRequest(HttpServletRequest request) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(header != null && header.startsWith("Bearer")){
            return header.substring(7);
        }

        return null;
    }
}
