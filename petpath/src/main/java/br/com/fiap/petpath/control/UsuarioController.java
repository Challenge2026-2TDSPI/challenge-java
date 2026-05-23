package br.com.fiap.petpath.control;

import br.com.fiap.petpath.dto.UsuarioDTO;
import br.com.fiap.petpath.mapper.UsuarioMapperAutomatico;
import br.com.fiap.petpath.model.StatusUsuarioEnum;
import br.com.fiap.petpath.model.Usuario;
import br.com.fiap.petpath.projection.UsuarioProjection;
import br.com.fiap.petpath.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repU;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioMapperAutomatico mapper;

    @GetMapping("/todos")
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        return ResponseEntity.ok(
                repU.findAll().stream().map(mapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        Optional<Usuario> op = repU.findById(id);
        if (op.isPresent()) return ResponseEntity.ok(mapper.toDTO(op.get()));
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/novo")
    public ResponseEntity<UsuarioDTO> inserir(@RequestBody Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        repU.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(usuario));
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (repU.existsById(id)) {
            repU.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/status")
    public ResponseEntity<List<UsuarioProjection>> buscarPorStatus(
            @RequestParam StatusUsuarioEnum status) {
        return ResponseEntity.ok(repU.buscarPorStatus(status.name()));
    }
}
