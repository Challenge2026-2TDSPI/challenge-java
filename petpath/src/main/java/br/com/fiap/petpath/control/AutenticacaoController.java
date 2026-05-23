package br.com.fiap.petpath.control;

import br.com.fiap.petpath.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/login")
    public String logar(
            @RequestParam String usuario,
            @RequestParam String senha,
            @RequestParam(value = "duracao", defaultValue = "60") Integer duracao) {
        try {
            var autenticacao = new UsernamePasswordAuthenticationToken(usuario, senha);
            manager.authenticate(autenticacao);
            return jwtUtil.gerarToken(usuario, duracao);
        } catch (Exception e) {
            return "Credenciais invalidas!";
        }
    }

}
