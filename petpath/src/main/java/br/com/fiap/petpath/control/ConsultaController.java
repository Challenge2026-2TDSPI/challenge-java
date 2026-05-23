package br.com.fiap.petpath.control;

import br.com.fiap.petpath.dto.ConsultaDTO;
import br.com.fiap.petpath.mapper.ConsultaMapperAutomatico;
import br.com.fiap.petpath.model.Consulta;
import br.com.fiap.petpath.model.StatusConsultaEnum;
import br.com.fiap.petpath.projection.ConsultaProjection;
import br.com.fiap.petpath.repository.ConsultaRepository;
import br.com.fiap.petpath.service.ConsultaCachingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaRepository repC;

    @Autowired
    private ConsultaCachingService cacheC;

    @Autowired
    private ConsultaMapperAutomatico mapper;

    @Operation(description = "Retorna consultas paginadas e ordenadas por id",
               summary = "Listar consultas paginadas",
               tags = "Retorno de Informacoes")
    @GetMapping("/paginadas")
    public ResponseEntity<Page<ConsultaDTO>> paginar(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size) {
        PageRequest pr = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<ConsultaDTO> resultado = cacheC.findAll(pr).map(c -> new ConsultaDTO(c));
        return ResponseEntity.ok(resultado);
    }

    @Operation(description = "Retorna todas as consultas cadastradas (caching)",
               summary = "Listar todas as consultas",
               tags = "Retorno de Informacoes")
    @GetMapping("/todas")
    public ResponseEntity<List<ConsultaDTO>> listarTodas() {
        List<ConsultaDTO> lista = cacheC.findAll().stream()
                .map(mapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @Operation(description = "Retorna uma consulta pelo ID",
               summary = "Buscar consulta por ID",
               tags = "Retorno de Informacoes")
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> buscarPorId(@PathVariable Long id) {
        Optional<Consulta> op = cacheC.findById(id);
        if (op.isPresent()) {
            return ResponseEntity.ok(mapper.toDTO(op.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(description = "Retorna projecoes de consultas a partir de uma data (SQL nativo + caching)",
               summary = "Buscar consultas por data",
               tags = "Retorno de Informacoes")
    @GetMapping("/por-data")
    public ResponseEntity<List<ConsultaProjection>> buscarPorData(@RequestParam LocalDate data) {
        return ResponseEntity.ok(cacheC.buscarPorData(data));
    }

    @Operation(description = "Retorna consultas filtradas por status via JPQL",
               summary = "Buscar consultas por status",
               tags = "Retorno de Informacoes")
    @GetMapping("/por-status")
    public ResponseEntity<List<ConsultaDTO>> buscarPorStatus(@RequestParam StatusConsultaEnum status) {
        List<ConsultaDTO> lista = repC.buscarPorStatus(status).stream()
                .map(mapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @Operation(description = "Agenda uma nova consulta para um pet",
               summary = "Agendar consulta",
               tags = "Persistencia de Informacoes")
    @PostMapping("/agendar")
    public ResponseEntity<ConsultaDTO> agendar(@RequestBody @Valid Consulta consulta) {
        repC.save(consulta);
        cacheC.removerCache();
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(consulta));
    }

    @Operation(description = "Atualiza os dados de uma consulta existente",
               summary = "Atualizar consulta",
               tags = "Atualizacao de Informacoes")
    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDTO> atualizar(
            @PathVariable Long id, @RequestBody @Valid Consulta consulta) {
        Optional<Consulta> op = cacheC.findById(id);
        if (op.isPresent()) {
            Consulta c = op.get();
            c.transferirConsulta(consulta);
            repC.save(c);
            cacheC.removerCache();
            return ResponseEntity.ok(mapper.toDTO(c));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(description = "Remove uma consulta pelo ID",
               summary = "Remover consulta",
               tags = "Remocao de Informacoes")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        Optional<Consulta> op = cacheC.findById(id);
        if (op.isPresent()) {
            repC.delete(op.get());
            cacheC.removerCache();
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
