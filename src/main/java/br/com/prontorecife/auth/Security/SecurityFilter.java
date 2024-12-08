package br.com.prontorecife.auth.Security;

import br.com.prontorecife.auth.Exceptions.AuthorizationHeaderException;
import br.com.prontorecife.auth.Service.TokenService;
import br.com.prontorecife.auth.Exceptions.CustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException, AuthenticationException {
        if (request.getRequestURI().startsWith("/swagger")
                || request.getRequestURI().startsWith("/v3/api-docs")
                || request.getRequestURI().startsWith("/auth/login")
                || request.getRequestURI().startsWith("/auth/logout")){
            filterChain.doFilter(request, response);
            return;
        }
        String token = this.recoverToken(request);
        log.info(token);
        if (tokenService.isTokenInvalid(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token invalido ou expirado!");
            return;
        }
        filterChain.doFilter(request, response);
    }
    private String recoverToken (HttpServletRequest request){
        String authHeader = String.valueOf(request.getHeader("x-auth"));
        if (authHeader == null || authHeader.isEmpty()) {
            throw new AuthorizationHeaderException();
        }
        return authHeader;
    }
}