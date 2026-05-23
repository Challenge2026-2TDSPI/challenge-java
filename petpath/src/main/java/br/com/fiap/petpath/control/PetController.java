package br.com.fiap.petpath.control;

import br.com.fiap.petpath.dto.PetDTO;
import br.com.fiap.petpath.mapper.PetMapperAutomatico;
import br.com.fiap.petpath.model.Pet;
import br.com.fiap.petpath.projection.ConsultaProjection;
import br.com.fiap.petpath.repository.PetRepository;
import br.com.fiap.petpath.service.PetCachingService;
import br.com.fiap.petpath.service.PetPaginacaoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetRepository repP;

    @Autowired
    private PetPaginacaoService paginacaoP;

    @Autowired
    private PetCachingService cacheP;

    @Autowired
    private PetMapperAutomatico mapper;

    // --- RETORNO ---

    @Operation(description = "Retorna todos os pets paginados e ordenados",
               summary = "Listar pets paginados",
               tags = "Retorno de Informacoes")
    @GetMapping("/paginados")
    public ResponseEntity<Page<PetDTO>> paginar(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "ordem", defaultValue = "nome") String ordem) {
        PageRequest pr = PageRequest.of(page, size, Sort.by(ordem).ascending());
        return ResponseEntity.ok(paginacaoP.paginar(pr));
    }

    @Operation(description = "Retorna todos os pets cadastrados (com caching)",
               summary = "Listar todos os pets",
               tags = "Retorno de Informacoes")
    @GetMapping("/todos")
    public ResponseEntity<List<PetDTO>> listarTodos() {
        List<PetDTO> lista = cacheP.findAll().stream()
                .map(mapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @Operation(description = "Retorna um pet pelo seu ID (com caching)",
               summary = "Buscar pet por ID",
               tags = "Retorno de Informacoes")
    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> buscarPorId(@PathVariable Long id) {
        Optional<Pet> op = cacheP.findById(id);
        if (op.isPresent()) {
            return ResponseEntity.ok(mapper.toDTO(op.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(description = "Busca pets por substring do nome, raca ou nome do tutor (caching + SQL nativo)",
               summary = "Buscar pets por substring",
               tags = "Retorno de Informacoes")
    @GetMapping("/buscar")
    public ResponseEntity<List<ConsultaProjection>> buscarPorSubstring(
            @RequestParam String substring) {
        return ResponseEntity.ok(cacheP.buscarPorSubstring(substring));
    }

    @Operation(description = "Busca pets por raca usando Spring JPA Query Method",
               summary = "Buscar pets por raca",
               tags = "Retorno de Informacoes")
    @GetMapping("/raca")
    public ResponseEntity<List<PetDTO>> buscarPorRaca(@RequestParam String raca) {
        List<PetDTO> lista = cacheP.buscarPorRaca(raca).stream()
                .map(mapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @Operation(description = "Busca pets ativos com peso acima do valor informado (JPQL)",
               summary = "Buscar pets por peso minimo",
               tags = "Retorno de Informacoes")
    @GetMapping("/peso")
    public ResponseEntity<List<PetDTO>> buscarPorPesoMinimo(@RequestParam Double peso) {
        List<PetDTO> lista = repP.buscarAtivosComPesoAcimaDe(peso).stream()
                .map(mapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    // --- PERSISTENCIA ---

    @Operation(description = "Cadastra um novo pet na plataforma PetPath AI",
               summary = "Cadastrar novo pet",
               tags = "Persistencia de Informacoes")
    @PostMapping("/cadastrar")
    public ResponseEntity<PetDTO> cadastrar(@RequestBody @Valid Pet pet) {
        repP.save(pet);
        cacheP.removerCache();
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(pet));
    }

    // --- ATUALIZACAO ---

    @Operation(description = "Atualiza os dados de um pet existente",
               summary = "Atualizar pet",
               tags = "Atualizacao de Informacoes")
    @PutMapping("/{id}")
    public ResponseEntity<PetDTO> atualizar(@PathVariable Long id, @RequestBody @Valid Pet pet) {
        Optional<Pet> op = cacheP.findById(id);
        if (op.isPresent()) {
            Pet petBanco = op.get();
            petBanco.transferirPet(pet);
            repP.save(petBanco);
            cacheP.removerCache();
            return ResponseEntity.ok(mapper.toDTO(petBanco));
        }
        return ResponseEntity.notFound().build();
    }

    // --- REMOCAO ---

    @Operation(description = "Remove um pet pelo ID",
               summary = "Remover pet",
               tags = "Remocao de Informacoes")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        Optional<Pet> op = cacheP.findById(id);
        if (op.isPresent()) {
            repP.delete(op.get());
            cacheP.removerCache();
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
