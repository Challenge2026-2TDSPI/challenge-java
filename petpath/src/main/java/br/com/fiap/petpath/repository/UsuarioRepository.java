package br.com.fiap.petpath.repository;

import br.com.fiap.petpath.model.Usuario;
import br.com.fiap.petpath.projection.UsuarioProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByRm(String rm);

    @Query(nativeQuery = true,
           value = "select u.rm, t.nome, u.permissao, u.status, " +
                   "u.data_criacao, t.email " +
                   "from usuario u inner join tutor t on (t.id = u.fk_tutor) " +
                   "where u.status = :status")
    List<UsuarioProjection> buscarPorStatus(String status);
}
