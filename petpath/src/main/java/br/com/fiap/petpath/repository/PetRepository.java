package br.com.fiap.petpath.repository;

import br.com.fiap.petpath.model.EspecieEnum;
import br.com.fiap.petpath.model.Pet;
import br.com.fiap.petpath.projection.ConsultaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    // JPQL — buscar pets por especie (usando Enum)
    @Query("from Pet p where p.especie = :especie")
    List<Pet> buscarPorEspecie(EspecieEnum especie);

    // JPQL — buscar pets ativos com peso acima do informado
    @Query("from Pet p where p.ativo = true and p.peso >= :peso")
    List<Pet> buscarAtivosComPesoAcimaDe(Double peso);

    // Spring JPA Query Method — buscar por raca (parte do nome)
    List<Pet> findByRacaContainingIgnoreCase(String raca);

    // Native Query — buscar pets por substring do nome, raca ou tutor
    @Query(nativeQuery = true,
           value = "select distinct p.nome pet_nome, t.nome tutor_nome, " +
                   "p.especie consulta_tipo, p.raca consulta_status, " +
                   "cast(p.peso as varchar) consulta_descricao " +
                   "from pet p inner join tutor t on (p.fk_tutor = t.id) " +
                   "where (lower(p.nome) like lower(concat('%', :substring, '%'))) " +
                   "or (lower(t.nome) like lower(concat('%', :substring, '%'))) " +
                   "or (lower(p.raca) like lower(concat('%', :substring, '%'))) " +
                   "order by p.nome asc")
    List<ConsultaProjection> buscarPorSubstring(String substring);
}
