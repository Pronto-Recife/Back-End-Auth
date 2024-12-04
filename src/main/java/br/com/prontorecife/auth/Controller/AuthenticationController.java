package br.com.prontorecife.auth.Controller;

import br.com.prontorecife.auth.DTOs.AuthenticationRequestDTO;
import br.com.prontorecife.auth.DTOs.SessionDTO;
import br.com.prontorecife.auth.Service.AuthenticationService;
import br.com.prontorecife.auth.Service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/session")
    public ResponseEntity<Void> verifySession(@RequestBody SessionDTO request){
        tokenService.validateToken(request.getAccessToken());
        return ResponseEntity.ok().build();
    }
}
