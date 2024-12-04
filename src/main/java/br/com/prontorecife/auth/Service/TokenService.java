package br.com.prontorecife.auth.Service;

import br.com.prontorecife.auth.Exceptions.CustomException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.auth0.jwt.algorithms.Algorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TokenService {
    @Value("${jjwt.secret}")
    private String jjwtSecret;

    public String generateToken(String id){
        return Jwts.builder()
                .subject(id)
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
                .signWith(getSecretKey())
                .compact();
    }
    private SecretKey getSecretKey() {
        return new SecretKeySpec(jjwtSecret.getBytes(), "HmacSHA256");
    }
    public String validateToken(String token){
        Algorithm algorithm = encryptor(jjwtSecret);
        try {
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getToken();
        }catch (Exception e){
            throw new CustomException("Sessão invalida!", HttpStatus.FORBIDDEN, Map.of("error", e.getMessage()));
        }
    }
    public Algorithm encryptor(String jjwtSecret){
        return Algorithm.HMAC256(jjwtSecret);
    }
}