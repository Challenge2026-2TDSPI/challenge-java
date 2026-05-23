package br.com.fiap.petpath;

import br.com.fiap.petpath.model.EspecieEnum;
import br.com.fiap.petpath.model.Pet;
import br.com.fiap.petpath.model.Tutor;
import br.com.fiap.petpath.repository.PetRepository;
import br.com.fiap.petpath.service.PetCachingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitarios do PetCachingService — PetPath AI")
class PetServiceTest {

    @Mock
    private PetRepository repP;

    @InjectMocks
    private PetCachingService service;

    private Tutor tutor;
    private Pet pet1;
    private Pet pet2;

    @BeforeEach
    void setUp() {
        tutor = new Tutor(1L, "Maria Silva", "52998224725",
                "maria@petpath.com", "11999999999");

        pet1 = new Pet(1L, tutor, "Rex", EspecieEnum.CACHORRO,
                "Labrador", LocalDate.of(2021, 5, 10), 15.5, true);

        pet2 = new Pet(2L, tutor, "Mia", EspecieEnum.GATO,
                "Persa", LocalDate.of(2022, 3, 1), 4.2, true);
    }

    @Test
    @DisplayName("Deve retornar todos os pets cadastrados")
    void deveRetornarTodosOsPets() {
        when(repP.findAll()).thenReturn(Arrays.asList(pet1, pet2));

        List<Pet> resultado = service.findAll();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Rex", resultado.get(0).getNome());
        assertEquals("Mia", resultado.get(1).getNome());
        verify(repP, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar pet por ID quando existir")
    void deveRetornarPetPorId() {
        when(repP.findById(1L)).thenReturn(Optional.of(pet1));

        Optional<Pet> resultado = service.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Rex", resultado.get().getNome());
        assertEquals(EspecieEnum.CACHORRO, resultado.get().getEspecie());
        assertEquals(15.5, resultado.get().getPeso());
    }

    @Test
    @DisplayName("Deve retornar vazio quando pet nao existir")
    void deveRetornarVazioQuandoPetNaoExistir() {
        when(repP.findById(99L)).thenReturn(Optional.empty());

        Optional<Pet> resultado = service.findById(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Deve buscar pets por raca ignorando maiusculas")
    void deveBuscarPetsPorRaca() {
        when(repP.findByRacaContainingIgnoreCase("labrador"))
                .thenReturn(Arrays.asList(pet1));

        List<Pet> resultado = service.buscarPorRaca("labrador");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Labrador", resultado.get(0).getRaca());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando nenhum pet for encontrado")
    void deveRetornarListaVaziaQuandoNaoEncontrar() {
        when(repP.findByRacaContainingIgnoreCase("xyz"))
                .thenReturn(Arrays.asList());

        List<Pet> resultado = service.buscarPorRaca("xyz");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Pet deve ter tutor associado")
    void petDeveTermTutorAssociado() {
        assertNotNull(pet1.getTutor());
        assertEquals("Maria Silva", pet1.getTutor().getNome());
    }

    @Test
    @DisplayName("Health Score - pet ativo deve ter peso valido")
    void petAtivoDeveTermPesoValido() {
        assertTrue(pet1.getAtivo());
        assertNotNull(pet1.getPeso());
        assertTrue(pet1.getPeso() > 0);
    }
}
