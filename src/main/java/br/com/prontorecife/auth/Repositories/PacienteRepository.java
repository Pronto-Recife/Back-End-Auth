package br.com.prontorecife.auth.Repositories;

import br.com.prontorecife.auth.Models.PacienteModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteModel, String> {
    Optional<PacienteModel> findByCPF(@NotBlank @Pattern(regexp = "\\d{11}") String CPF);
    List<PacienteModel> findByResponsavelCpf(String CPF);

}
