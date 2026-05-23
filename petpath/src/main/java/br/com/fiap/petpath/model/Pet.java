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
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Entidade que representa o animal de estimacao cadastrado na plataforma PetPath AI")
@Entity
@Table(name = "pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_tutor")
    private Tutor tutor;

    @NotEmpty(message = "O nome do pet e obrigatorio")
    @Size(min = 1, max = 50, message = "O nome do pet deve ter entre 1 e 50 caracteres")
    @Schema(description = "Nome do pet", example = "Rex")
    private String nome;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "A especie e obrigatoria")
    @Schema(description = "Especie do pet: CACHORRO, GATO, PASSARO, COELHO, HAMSTER, OUTRO", example = "CACHORRO")
    private EspecieEnum especie;

    @NotEmpty(message = "A raca e obrigatoria")
    @Size(min = 2, max = 50, message = "A raca deve ter entre 2 e 50 caracteres")
    @Schema(description = "Raca do pet", example = "Labrador")
    private String raca;

    @PastOrPresent(message = "A data de nascimento deve ser atual ou passada")
    @Schema(description = "Data de nascimento do pet", example = "2020-03-15")
    private LocalDate data_nascimento;

    @DecimalMin(value = "0.1", message = "O peso deve ser maior que 0.1 kg")
    @DecimalMax(value = "200.0", message = "O peso nao pode ultrapassar 200 kg")
    @Schema(description = "Peso do pet em quilogramas", example = "12.5")
    private Double peso;

    @Schema(description = "Indica se o pet esta ativo na plataforma", example = "true")
    private Boolean ativo;

    public void transferirPet(Pet pet) {
        this.tutor = pet.getTutor();
        this.nome = pet.getNome();
        this.especie = pet.getEspecie();
        this.raca = pet.getRaca();
        this.data_nascimento = pet.getData_nascimento();
        this.peso = pet.getPeso();
        this.ativo = pet.getAtivo();
    }

    public Pet() {}

    public Pet(Long id, Tutor tutor, String nome, EspecieEnum especie,
               String raca, LocalDate data_nascimento, Double peso, Boolean ativo) {
        this.id = id;
        this.tutor = tutor;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.data_nascimento = data_nascimento;
        this.peso = peso;
        this.ativo = ativo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
