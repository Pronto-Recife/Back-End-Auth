package br.com.prontorecife.auth.Service;

import br.com.prontorecife.auth.DTOs.AuthenticationRequestDTO;
import br.com.prontorecife.auth.Enum.LoginFlowEnum;
import br.com.prontorecife.auth.Exceptions.CustomException;
import br.com.prontorecife.auth.Models.MedicoModel;
import br.com.prontorecife.auth.Models.PacienteModel;
import br.com.prontorecife.auth.Repositories.MedicoRepository;
import br.com.prontorecife.auth.Repositories.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public String authenticate(AuthenticationRequestDTO request){
        if (request.getFlow() == LoginFlowEnum.CRM) {
            MedicoModel medico = getMedico(request.getIdentificador());
            if (!passwordEncoder.matches(request.getSenha(), medico.getSenha())) {
                throw new CustomException("CRM ou Senha Incorreta!", HttpStatus.UNAUTHORIZED, null);
            }
            return tokenService.generateToken(medico.getId());
        }
        if (request.getFlow() == LoginFlowEnum.CPF) {
            PacienteModel paciente = getPaciente(request.getIdentificador());
            if (!passwordEncoder.matches(request.getSenha(), paciente.getSenha())) {
                throw new CustomException("CPF ou Senha Incorreta!", HttpStatus.UNAUTHORIZED, null);
            }
            return tokenService.generateToken(paciente.getId());
        }
        return "Invalid Flow!";
    }
    public MedicoModel getMedico(String crm){
        return medicoRepository.findByCRM(crm).orElseThrow(() ->
                new RuntimeException("Medico Não Encontrado!"));
    }
    public PacienteModel getPaciente(String cpf){
        return pacienteRepository.findByCPF(cpf).orElseThrow(() ->
                new RuntimeException("Paciente Não Encontrado!"));
    }
}