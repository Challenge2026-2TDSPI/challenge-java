package br.com.fiap.petpath.dto;

import br.com.fiap.petpath.model.StatusUsuarioEnum;
import br.com.fiap.petpath.model.Tutor;

import java.time.LocalDate;

public class UsuarioDTO {

    private Tutor tutor;
    private String rm;
    private String permissao;
    private LocalDate dataCriacao;
    private StatusUsuarioEnum status;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Tutor tutor, String rm, String permissao,
                      LocalDate dataCriacao, StatusUsuarioEnum status) {
        this.tutor = tutor;
        this.rm = rm;
        this.permissao = permissao;
        this.dataCriacao = dataCriacao;
        this.status = status;
    }

    public Tutor getTutor() { return tutor; }
    public void setTutor(Tutor tutor) { this.tutor = tutor; }
    public String getRm() { return rm; }
    public void setRm(String rm) { this.rm = rm; }
    public String getPermissao() { return permissao; }
    public void setPermissao(String permissao) { this.permissao = permissao; }
    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }
    public StatusUsuarioEnum getStatus() { return status; }
    public void setStatus(StatusUsuarioEnum status) { this.status = status; }
}
