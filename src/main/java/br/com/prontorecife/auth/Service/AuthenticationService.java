package br.com.prontorecife.auth.Service;

import br.com.prontorecife.auth.DTOs.AuthenticationRequestDTO;
import br.com.prontorecife.auth.Enum.LoginFlowEnum;
import br.com.prontorecife.auth.Service.TokenService;
import br.com.prontorecife.auth.Exceptions.CustomException;
import br.com.prontorecife.auth.Models.EstabelecimentoModel;
import br.com.prontorecife.auth.Models.MedicoModel;
import br.com.prontorecife.auth.Models.PacienteModel;
import br.com.prontorecife.auth.Repositories.EstabelecimentoRepository;
import br.com.prontorecife.auth.Repositories.MedicoRepository;
import br.com.prontorecife.auth.Repositories.PacienteRepository;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Value("${jjwt.secret}")
    private String jjwtSecret;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public String authenticate(AuthenticationRequestDTO request){
        /*if (request.getFlow() == LoginFlowEnum.CNPJ) {
            EstabelecimentoModel estabelecimento = getEstabelecimento(request.getIdentificador());
            if (!passwordEncoder.matches(request.getSenha(), estabelecimento.getSenha())) {
                throw new CustomException("CNPJ ou Senha Incorreta!", HttpStatus.UNAUTHORIZED, null);
            }
            return tokenService.generateToken(estabelecimento.getId());
        }*/
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
    /*public EstabelecimentoModel getEstabelecimento(String cnpj){
        return estabelecimentoRepository.findByCnpj(cnpj).orElseThrow(() ->
                new RuntimeException("Estabelecimento Não Encontrado!"));
    }*/
}