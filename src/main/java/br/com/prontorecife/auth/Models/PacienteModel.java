package br.com.prontorecife.auth.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "paciente")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteModel{
    @Id
    private String id;
    @Column(name = "cpf", unique = true, length = 14)
    private String CPF;
    @Column(name = "nome_completo")
    private String nomeCompleto;
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    private String genero;
    @Column(name = "email", unique = true)
    private String email;
    private String senha;
    private String telefone;
    @Column(name = "contato_representante")
    private String contatoRepresentante;
    private String cep;
    private String endereco;
    @Column(name = "responsavel_cpf", length = 14)
    private String responsavelCpf;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
}