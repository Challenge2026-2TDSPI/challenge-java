package br.com.fiap.petpath.service;

import br.com.fiap.petpath.dto.PetDTO;
import br.com.fiap.petpath.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PetPaginacaoService {

    @Autowired
    private PetCachingService cacheP;

    public Page<PetDTO> paginar(PageRequest req) {
        Page<Pet> paginados = cacheP.findAll(req);
        return paginados.map(pet -> new PetDTO(pet));
    }

}
