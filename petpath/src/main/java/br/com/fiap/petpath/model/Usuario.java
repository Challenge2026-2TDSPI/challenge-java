package br.com.fiap.petpath.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_tutor")
    private Tutor tutor;

    private String rm;
    private String senha;
    private String permissao;
    private LocalDate dataCriacao;

    @Enumerated(EnumType.STRING)
    private StatusUsuarioEnum status;

    public Usuario() {}

    public Usuario(Long id, Tutor tutor, String rm, String senha,
                   String permissao, LocalDate dataCriacao, StatusUsuarioEnum status) {
        this.id = id;
        this.tutor = tutor;
        this.rm = rm;
        this.senha = senha;
        this.permissao = permissao;
        this.dataCriacao = dataCriacao;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Tutor getTutor() { return tutor; }
    public void setTutor(Tutor tutor) { this.tutor = tutor; }
    public String getRm() { return rm; }
    public void setRm(String rm) { this.rm = rm; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getPermissao() { return permissao; }
    public void setPermissao(String permissao) { this.permissao = permissao; }
    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }
    public StatusUsuarioEnum getStatus() { return status; }
    public void setStatus(StatusUsuarioEnum status) { this.status = status; }
}
