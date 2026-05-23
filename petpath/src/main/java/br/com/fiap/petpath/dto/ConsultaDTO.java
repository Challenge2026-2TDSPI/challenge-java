package br.com.fiap.petpath.dto;

import br.com.fiap.petpath.model.Consulta;
import br.com.fiap.petpath.model.Pet;
import br.com.fiap.petpath.model.StatusConsultaEnum;
import br.com.fiap.petpath.model.TipoConsultaEnum;

import java.time.LocalDate;

public class ConsultaDTO {

    private Pet pet;
    private LocalDate data_consulta;
    private TipoConsultaEnum tipo;
    private String descricao;
    private StatusConsultaEnum status;

    public ConsultaDTO() {
    }

    public ConsultaDTO(Consulta consulta) {
        this.pet = consulta.getPet();
        this.data_consulta = consulta.getData_consulta();
        this.tipo = consulta.getTipo();
        this.descricao = consulta.getDescricao();
        this.status = consulta.getStatus();
    }

    public ConsultaDTO(Pet pet, LocalDate data_consulta, TipoConsultaEnum tipo,
                       String descricao, StatusConsultaEnum status) {
        this.pet = pet;
        this.data_consulta = data_consulta;
        this.tipo = tipo;
        this.descricao = descricao;
        this.status = status;
    }

    public Pet getPet() { return pet; }
    public void setPet(Pet pet) { this.pet = pet; }
    public LocalDate getData_consulta() { return data_consulta; }
    public void setData_consulta(LocalDate data_consulta) { this.data_consulta = data_consulta; }
    public TipoConsultaEnum getTipo() { return tipo; }
    public void setTipo(TipoConsultaEnum tipo) { this.tipo = tipo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public StatusConsultaEnum getStatus() { return status; }
    public void setStatus(StatusConsultaEnum status) { this.status = status; }
}
