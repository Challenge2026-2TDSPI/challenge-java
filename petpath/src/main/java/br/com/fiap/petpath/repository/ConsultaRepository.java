package br.com.fiap.petpath.repository;

import br.com.fiap.petpath.model.Consulta;
import br.com.fiap.petpath.model.StatusConsultaEnum;
import br.com.fiap.petpath.model.TipoConsultaEnum;
import br.com.fiap.petpath.projection.ConsultaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    // JPQL — consultas por tipo
    @Query("from Consulta c where c.tipo = :tipo")
    List<Consulta> buscarPorTipo(TipoConsultaEnum tipo);

    // JPQL — consultas por status (usando Enum direto)
    @Query("from Consulta c where c.status = :status")
    List<Consulta> buscarPorStatus(StatusConsultaEnum status);

    // Native Query — consultas com dados do pet e tutor por data
    @Query(nativeQuery = true,
           value = "select p.nome as pet_nome, t.nome as tutor_nome, " +
                   "cast(c.tipo as varchar) as consulta_tipo, " +
                   "cast(c.status as varchar) as consulta_status, " +
                   "c.descricao as consulta_descricao " +
                   "from consulta c " +
                   "inner join pet p on (c.fk_pet = p.id) " +
                   "inner join tutor t on (p.fk_tutor = t.id) " +
                   "where c.data_consulta >= :data_param " +
                   "order by c.data_consulta asc")
    List<ConsultaProjection> buscarConsultasPorData(LocalDate data_param);
}
