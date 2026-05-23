package br.com.fiap.petpath.dto;

import br.com.fiap.petpath.model.EspecieEnum;
import br.com.fiap.petpath.model.Pet;
import br.com.fiap.petpath.model.Tutor;

import java.time.LocalDate;

public class PetDTO {

    private Tutor tutor;
    private String nome;
    private EspecieEnum especie;
    private String raca;
    private LocalDate data_nascimento;
    private Double peso;
    private Boolean ativo;

    public PetDTO() {
    }

    public PetDTO(Pet pet) {
        this.tutor = pet.getTutor();
        this.nome = pet.getNome();
        this.especie = pet.getEspecie();
        this.raca = pet.getRaca();
        this.data_nascimento = pet.getData_nascimento();
        this.peso = pet.getPeso();
        this.ativo = pet.getAtivo();
    }

    public PetDTO(Tutor tutor, String nome, EspecieEnum especie,
                  String raca, LocalDate data_nascimento, Double peso, Boolean ativo) {
        this.tutor = tutor;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.data_nascimento = data_nascimento;
        this.peso = peso;
        this.ativo = ativo;
    }

    public Tutor getTutor() { return tutor; }
    public void setTutor(Tutor tutor) { this.tutor = tutor; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public EspecieEnum getEspecie() { return especie; }
    public void setEspecie(EspecieEnum especie) { this.especie = especie; }
    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }
    public LocalDate getData_nascimento() { return data_nascimento; }
    public void setData_nascimento(LocalDate data_nascimento) { this.data_nascimento = data_nascimento; }
    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}
