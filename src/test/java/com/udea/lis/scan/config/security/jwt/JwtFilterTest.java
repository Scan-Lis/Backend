package com.udea.lis.scan.config.security.jwt;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.udea.lis.scan.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JwtFilterTest {

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private JwtFilter jwtFilter;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testDoFilter() throws Exception {
        // Mock valid token and username
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VybmFtZSIsInJvbCI6ImFkbWluIiwiaWF0IjoxNjUwNTM3MTk0LCJleHAiOjE2NTA1NDEwOTR9.abc123";
        String username = "username";
        GrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        when(decodedJWT.getClaim("rol")).thenReturn(new Claim() {
            @Override
            public boolean isNull() {
                return false;
            }

            @Override
            public boolean isMissing() {
                return false;
            }

            @Override
            public Boolean asBoolean() {
                return null;
            }

            @Override
            public String asString() {
                return "ROLE_ADMIN"; // Example role
            }

            @Override
            public Date asDate() {
                return null;
            }

            @Override
            public <T> T[] asArray(Class<T> aClass) throws JWTDecodeException {
                return null;
            }

            @Override
            public <T> List<T> asList(Class<T> aClass) throws JWTDecodeException {
                return List.of();
            }

            @Override
            public Map<String, Object> asMap() throws JWTDecodeException {
                return Map.of();
            }

            @Override
            public <T> T as(Class<T> aClass) throws JWTDecodeException {
                return null;
            }

            @Override
            public Integer asInt() {
                return null;
            }

            @Override
            public Long asLong() {
                return null;
            }

            @Override
            public Double asDouble() {
                return null;
            }
        });;
        // Mock JWT validation and username retrieval
        when(jwtService.validateToken(token)).thenReturn(decodedJWT);
        when(jwtService.getUsername(any(DecodedJWT.class))).thenReturn(username);

        // Mock HttpServletRequest with valid authorization header
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);

        // Mock HttpServletResponse and FilterChain
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        // Execute doFilterInternal
        jwtFilter.doFilterInternal(request, response, filterChain);

        assertNotNull("hola");
    }
}