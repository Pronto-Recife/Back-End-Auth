package br.com.prontorecife.auth.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Entity
@Table(name = "medico")
@Getter @Setter
public class MedicoModel{

    @Id
    private String id;
    @Column(name = "CRM", unique = true)
    private String CRM;
    @Column(name = "nome_completo")
    private String nomeCompleto;
    private String telefone;
    private String email;
    @Column(name = "senha")
    private String senha;
    @Column (name = "id_estabelecimento")
    private String idEstabelecimento;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
}
