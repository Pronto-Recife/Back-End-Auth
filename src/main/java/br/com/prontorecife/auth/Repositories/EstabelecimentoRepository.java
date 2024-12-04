package br.com.prontorecife.auth.Repositories;

import br.com.prontorecife.auth.Models.EstabelecimentoModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EstabelecimentoRepository extends JpaRepository<EstabelecimentoModel, String> {
    Optional<EstabelecimentoModel> findByEmail(@NotBlank String email);
    Optional<EstabelecimentoModel> findByCnpj(@NotNull @NotBlank String cnpj);
}
