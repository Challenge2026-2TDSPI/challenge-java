package br.com.fiap.petpath.service;

import br.com.fiap.petpath.model.Pet;
import br.com.fiap.petpath.projection.ConsultaProjection;
import br.com.fiap.petpath.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetCachingService {

    @Autowired
    private PetRepository repP;

    @Cacheable(value = "todosPets")
    public List<Pet> findAll() {
        return repP.findAll();
    }

    @Cacheable(value = "petPorId", key = "#id")
    public Optional<Pet> findById(Long id) {
        return repP.findById(id);
    }

    @Cacheable(value = "petsPaginados", key = "#pr")
    public Page<Pet> findAll(PageRequest pr) {
        return repP.findAll(pr);
    }

    @Cacheable(value = "petsPorSubstring", key = "#substring")
    public List<ConsultaProjection> buscarPorSubstring(String substring) {
        return repP.buscarPorSubstring(substring);
    }

    @Cacheable(value = "petsPorRaca", key = "#raca")
    public List<Pet> buscarPorRaca(String raca) {
        return repP.findByRacaContainingIgnoreCase(raca);
    }

    @CacheEvict(value = {"todosPets", "petPorId", "petsPaginados",
            "petsPorSubstring", "petsPorRaca"}, allEntries = true)
    public void removerCache() {
        System.out.println("Cache de pets removido");
    }

}
