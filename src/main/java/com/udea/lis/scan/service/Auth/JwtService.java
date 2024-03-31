package com.udea.lis.scan.service.Auth;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class JwtService {
    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.time.expiration}")
    private String timeExpiration;
    private String userGenerator = "AUTH0JWT-BACKEND";

    //generate token
    public String createToken(Authentication auth){
        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
        String username = auth.getPrincipal().toString();
        System.out.println("Username: " + username);
        String rol = auth.getAuthorities()
                .stream()
                .map(role -> role.getAuthority())
                .findFirst().orElseThrow();
        return JWT.create()
                .withIssuer(userGenerator)
                .withSubject(username)
                .withClaim("rol", rol)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .withJWTId("JWT-" + username)
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);

    }

    //validate token
    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(userGenerator)
                    .build();
            return verifier.verify(token);
        } catch (JWTVerificationException exception){
            throw  new JWTVerificationException("Token invalido");
        }
    }


    //Obtener username
    public String getUsername(DecodedJWT jwt){
        return jwt.getSubject();
    }

    //Obtener rol
    public String getRol(DecodedJWT jwt){
        return jwt.getClaim("rol").toString();
    }

    //obtener claim en especifico
    public Claim getClaim(DecodedJWT jwt, String claim){
        return jwt.getClaim(claim);
    }

    //Obtener todos los claims
    public Map<String, Claim> getClaims(DecodedJWT jwt){
        return jwt.getClaims();
    }


}
