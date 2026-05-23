package br.com.fiap.petpath.mapper;

import br.com.fiap.petpath.dto.UsuarioDTO;
import br.com.fiap.petpath.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapperAutomatico {

    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;
        UsuarioDTO dto = new UsuarioDTO();
        dto.setTutor(usuario.getTutor());
        dto.setRm(usuario.getRm());
        dto.setPermissao(usuario.getPermissao());
        dto.setDataCriacao(usuario.getDataCriacao());
        dto.setStatus(usuario.getStatus());
        return dto;
    }

    public Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;
        Usuario u = new Usuario();
        u.setTutor(dto.getTutor());
        u.setRm(dto.getRm());
        u.setPermissao(dto.getPermissao());
        u.setDataCriacao(dto.getDataCriacao());
        u.setStatus(dto.getStatus());
        return u;
    }
}
