package br.com.prontorecife.auth.Controller;

import br.com.prontorecife.auth.DTOs.AuthenticationRequestDTO;
import br.com.prontorecife.auth.DTOs.SessionDTO;
import br.com.prontorecife.auth.Service.AuthenticationService;
import br.com.prontorecife.auth.Service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {
    private final TokenService tokenService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public SessionDTO login(@RequestBody AuthenticationRequestDTO request){
        String token = authenticationService.authenticate(request);
        return new SessionDTO(token);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody SessionDTO request){

        long expirationTimeInSeconds = 86400;
        tokenService.invalidateToken(request.getAccessToken(), expirationTimeInSeconds);

        return ResponseEntity.ok("Logout realizado com sucesso!");
    }
    @PostMapping("/session")
    public ResponseEntity<Void> verifySession(@RequestBody SessionDTO request){
        boolean valid = tokenService.validateToken(request.getAccessToken());
        return valid ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
