package br.com.prontorecife.auth.Controller;

import br.com.prontorecife.auth.DTOs.AuthenticationRequestDTO;
import br.com.prontorecife.auth.DTOs.SessionDTO;
import br.com.prontorecife.auth.Service.AuthenticationService;
import br.com.prontorecife.auth.Service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final TokenService tokenService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public SessionDTO login(@RequestBody AuthenticationRequestDTO request){
        String token = authenticationService.authenticate(request);
        return new SessionDTO(token);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token){
        String processedToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        long expirationTimeInSeconds = 86400;
        tokenService.invalidateToken(processedToken, expirationTimeInSeconds);

        return ResponseEntity.ok("Logout realizado com sucesso!");
    }
    @PostMapping("/session")
    public ResponseEntity<Void> verifySession(@RequestBody SessionDTO request){
        tokenService.validateToken(request.getAccessToken());
        return ResponseEntity.ok().build();
    }
}
