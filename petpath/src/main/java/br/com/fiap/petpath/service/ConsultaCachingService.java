package br.com.fiap.petpath.service;

import br.com.fiap.petpath.model.Consulta;
import br.com.fiap.petpath.projection.ConsultaProjection;
import br.com.fiap.petpath.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaCachingService {

    @Autowired
    private ConsultaRepository repC;

    @Cacheable(value = "todasConsultas")
    public List<Consulta> findAll() {
        return repC.findAll();
    }

    @Cacheable(value = "consultaPorId", key = "#id")
    public Optional<Consulta> findById(Long id) {
        return repC.findById(id);
    }

    @Cacheable(value = "consultasPaginadas", key = "#pr")
    public Page<Consulta> findAll(PageRequest pr) {
        return repC.findAll(pr);
    }

    @Cacheable(value = "consultasPorData", key = "#data")
    public List<ConsultaProjection> buscarPorData(LocalDate data) {
        return repC.buscarConsultasPorData(data);
    }

    @CacheEvict(value = {"todasConsultas", "consultaPorId",
            "consultasPaginadas", "consultasPorData"}, allEntries = true)
    public void removerCache() {
        System.out.println("Cache de consultas removido");
    }

}
