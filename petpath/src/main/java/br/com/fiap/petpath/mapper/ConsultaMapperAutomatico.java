package br.com.fiap.petpath.mapper;

import br.com.fiap.petpath.dto.ConsultaDTO;
import br.com.fiap.petpath.model.Consulta;
import org.springframework.stereotype.Component;

@Component
public class ConsultaMapperAutomatico {

    public ConsultaDTO toDTO(Consulta consulta) {
        if (consulta == null) return null;
        ConsultaDTO dto = new ConsultaDTO();
        dto.setPet(consulta.getPet());
        dto.setData_consulta(consulta.getData_consulta());
        dto.setTipo(consulta.getTipo());
        dto.setDescricao(consulta.getDescricao());
        dto.setStatus(consulta.getStatus());
        return dto;
    }

    public Consulta toEntity(ConsultaDTO dto) {
        if (dto == null) return null;
        Consulta c = new Consulta();
        c.setPet(dto.getPet());
        c.setData_consulta(dto.getData_consulta());
        c.setTipo(dto.getTipo());
        c.setDescricao(dto.getDescricao());
        c.setStatus(dto.getStatus());
        return c;
    }
}
