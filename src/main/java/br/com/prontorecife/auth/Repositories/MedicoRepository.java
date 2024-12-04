package br.com.prontorecife.auth.Repositories;

import br.com.prontorecife.auth.Models.MedicoModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<MedicoModel, String>  {
    Optional<MedicoModel> findByCRM(String CRM);
    @Transactional
    void deleteByCRM(String CRM);
}
