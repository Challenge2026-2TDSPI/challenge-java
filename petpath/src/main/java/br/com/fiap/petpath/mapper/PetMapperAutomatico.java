package br.com.fiap.petpath.mapper;

import br.com.fiap.petpath.dto.PetDTO;
import br.com.fiap.petpath.model.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetMapperAutomatico {

    public PetDTO toDTO(Pet pet) {
        if (pet == null) return null;
        PetDTO dto = new PetDTO();
        dto.setTutor(pet.getTutor());
        dto.setNome(pet.getNome());
        dto.setEspecie(pet.getEspecie());
        dto.setRaca(pet.getRaca());
        dto.setData_nascimento(pet.getData_nascimento());
        dto.setPeso(pet.getPeso());
        dto.setAtivo(pet.getAtivo());
        return dto;
    }

    public Pet toEntity(PetDTO dto) {
        if (dto == null) return null;
        Pet pet = new Pet();
        pet.setTutor(dto.getTutor());
        pet.setNome(dto.getNome());
        pet.setEspecie(dto.getEspecie());
        pet.setRaca(dto.getRaca());
        pet.setData_nascimento(dto.getData_nascimento());
        pet.setPeso(dto.getPeso());
        pet.setAtivo(dto.getAtivo());
        return pet;
    }
}
