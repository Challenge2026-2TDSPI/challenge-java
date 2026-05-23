package br.com.fiap.petpath.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Entidade que representa uma consulta veterinaria vinculada a um pet")
@Entity
@Table(name = "consulta")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_pet")
    private Pet pet;

    @NotNull(message = "A data da consulta e obrigatoria")
    @Schema(description = "Data da consulta", example = "2025-06-10")
    private LocalDate data_consulta;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O tipo de consulta e obrigatorio")
    @Schema(description = "Tipo: PREVENTIVA, TERAPEUTICA, BEM_ESTAR, EMERGENCIA, RETORNO", example = "PREVENTIVA")
    private TipoConsultaEnum tipo;

    @NotEmpty(message = "A descricao da consulta e obrigatoria")
    @Size(min = 5, max = 200, message = "A descricao deve ter entre 5 e 200 caracteres")
    @Schema(description = "Descricao do procedimento ou motivo da consulta", example = "Vacinacao anual antirabica")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status: AGENDADA, REALIZADA, CANCELADA", example = "AGENDADA")
    private StatusConsultaEnum status;

    public void transferirConsulta(Consulta c) {
        this.pet = c.getPet();
        this.data_consulta = c.getData_consulta();
        this.tipo = c.getTipo();
        this.descricao = c.getDescricao();
        this.status = c.getStatus();
    }

    public Consulta() {}

    public Consulta(Long id, Pet pet, LocalDate data_consulta,
                    TipoConsultaEnum tipo, String descricao, StatusConsultaEnum status) {
        this.id = id;
        this.pet = pet;
        this.data_consulta = data_consulta;
        this.tipo = tipo;
        this.descricao = descricao;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
