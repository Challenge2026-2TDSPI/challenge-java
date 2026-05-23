package br.com.fiap.petpath.repository;

import br.com.fiap.petpath.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
    List<Tutor> findByNomeContainingIgnoreCase(String nome);
}
