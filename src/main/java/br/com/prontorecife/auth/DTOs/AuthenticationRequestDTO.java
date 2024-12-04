package br.com.prontorecife.auth.DTOs;

import br.com.prontorecife.auth.Enum.LoginFlowEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequestDTO {
    private String identificador;
    private String senha;
    private LoginFlowEnum flow;
}
