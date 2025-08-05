package br.com.sampaiollo.pzsmp.controller;

import br.com.sampaiollo.pzsmp.dto.LoginRequestDto;
import br.com.sampaiollo.pzsmp.dto.LoginResponseDto;
import br.com.sampaiollo.pzsmp.entity.Funcionario; // <<< IMPORTE A ENTIDADE FUNCIONARIO
import br.com.sampaiollo.pzsmp.entity.Usuario;
import br.com.sampaiollo.pzsmp.repository.FuncionarioRepository; // <<< IMPORTE O REPOSITÓRIO
import br.com.sampaiollo.pzsmp.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private FuncionarioRepository funcionarioRepository; // <<< INJETE O REPOSITÓRIO AQUI

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto data) {
        // 1. Autentica o usuário (continua igual)
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // 2. Pega o objeto do usuário autenticado
        var usuario = (Usuario) auth.getPrincipal();

        // 3. <<< NOVO PASSO >>>
        // Usa o login do usuário para encontrar o funcionário correspondente
        Funcionario funcionario = funcionarioRepository.findByUsuarioLogin(usuario.getLogin())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado para o usuário logado."));

        // 4. Gera o token JWT
        var token = tokenService.gerarToken(usuario);

        // 5. Pega os dados para a resposta
        String nomeUsuario = funcionario.getNome(); // O nome vem da entidade Funcionario (que herda de Pessoa)
        String cargoUsuario = usuario.getTipo().name(); // O cargo/permissão vem do Enum 'tipo' da entidade Usuario

        // 6. Cria a nova resposta completa
        var response = new LoginResponseDto(token, nomeUsuario, cargoUsuario);

        // 7. Retorna a resposta para o frontend
        return ResponseEntity.ok(response);
    }
}