package br.com.fiap.petpath.control;

import br.com.fiap.petpath.model.Tutor;
import br.com.fiap.petpath.repository.TutorRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    private TutorRepository repT;

    @Operation(summary = "Listar todos os tutores", tags = "Retorno de Informacoes")
    @GetMapping("/todos")
    public ResponseEntity<List<Tutor>> listarTodos() {
        return ResponseEntity.ok(repT.findAll());
    }

    @Operation(summary = "Buscar tutor por ID", tags = "Retorno de Informacoes")
    @GetMapping("/{id}")
    public ResponseEntity<Tutor> buscarPorId(@PathVariable Long id) {
        Optional<Tutor> op = repT.findById(id);
        return op.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cadastrar novo tutor", tags = "Persistencia de Informacoes")
    @PostMapping("/cadastrar")
    public ResponseEntity<Tutor> cadastrar(@RequestBody @Valid Tutor tutor) {
        repT.save(tutor);
        return ResponseEntity.status(HttpStatus.CREATED).body(tutor);
    }

    @Operation(summary = "Atualizar tutor", tags = "Atualizacao de Informacoes")
    @PutMapping("/{id}")
    public ResponseEntity<Tutor> atualizar(@PathVariable Long id, @RequestBody @Valid Tutor tutor) {
        Optional<Tutor> op = repT.findById(id);
        if (op.isPresent()) {
            Tutor t = op.get();
            t.setNome(tutor.getNome());
            t.setCpf(tutor.getCpf());
            t.setEmail(tutor.getEmail());
            t.setTelefone(tutor.getTelefone());
            repT.save(t);
            return ResponseEntity.ok(t);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Remover tutor", tags = "Remocao de Informacoes")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (repT.existsById(id)) {
            repT.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
