package br.com.fiap.petpath.security;

import br.com.fiap.petpath.model.Usuario;
import br.com.fiap.petpath.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UsuarioConfig {

    @Autowired
    private UsuarioRepository repU;

    @Bean
    public UserDetailsService gerarUsuario() {
        return rm -> {
            Usuario usuario = repU.findByRm(rm)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("Usuario nao localizado: " + rm));
            return User.builder()
                    .username(usuario.getRm())
                    .password(usuario.getSenha())
                    .roles(usuario.getPermissao())
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
