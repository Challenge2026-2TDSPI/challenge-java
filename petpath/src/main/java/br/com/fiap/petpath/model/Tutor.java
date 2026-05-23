package br.com.fiap.petpath.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

@Schema(description = "Entidade que representa o tutor responsavel pelo pet")
@Entity
@Table(name = "tutor")
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O nome do tutor e obrigatorio")
    @Size(min = 2, max = 80, message = "O nome deve ter entre 2 e 80 caracteres")
    @Schema(description = "Nome completo do tutor", example = "Maria Silva")
    @Column(name = "nome")
    private String nome;

    @CPF(message = "CPF invalido")
    @Schema(description = "CPF do tutor (somente numeros)", example = "12345678909")
    private String cpf;

    @NotEmpty(message = "O e-mail e obrigatorio")
    @Email(message = "E-mail invalido")
    @Schema(description = "E-mail do tutor", example = "maria@email.com")
    private String email;

    @Size(min = 10, max = 15, message = "Telefone deve ter entre 10 e 15 caracteres")
    @Schema(description = "Telefone do tutor", example = "11987654321")
    private String telefone;

    public Tutor() {}

    public Tutor(Long id, String nome, String cpf, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
