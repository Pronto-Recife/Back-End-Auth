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
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException, AuthenticationException {
        if (request.getRequestURI().startsWith("/swagger")
                || request.getRequestURI().startsWith("/v3/api-docs")
                || request.getRequestURI().startsWith("/auth/register")
                || request.getRequestURI().startsWith("/auth/login")
                || request.getRequestURI().startsWith("/auth/session")
                || request.getRequestURI().startsWith("/test-redis")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = this.recoverToken(request);
        String validatedToken = tokenService.validateToken(token);
        //String email = tokenService.getEmailFromToken(validatedToken);
        if (token != null && token.startsWith("Bearer ")) {
            String processedToken = token.substring(7);
            if (tokenService.isTokenInvalid(processedToken)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token invalido ou expirado!");
                return;
            }
        }
        /*if (validatedToken.isEmpty()) {
            throw new CustomException("Não foi possivel receber o email de autenticação do usuário ou o token!", HttpStatus.UNAUTHORIZED, null);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);*/
        filterChain.doFilter(request, response);
    }
    private String recoverToken (@NonNull HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isEmpty()) {
            throw new AuthorizationHeaderException();
        }
        return authHeader.substring(7);
    }
}