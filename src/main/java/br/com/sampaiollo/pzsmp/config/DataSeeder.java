package br.com.sampaiollo.pzsmp.config;

import br.com.sampaiollo.pzsmp.entity.Funcionario;
import br.com.sampaiollo.pzsmp.entity.TipoUsuario;
import br.com.sampaiollo.pzsmp.entity.Usuario;
import br.com.sampaiollo.pzsmp.repository.FuncionarioRepository;
import br.com.sampaiollo.pzsmp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se o usuário 'admin' já existe para não criar duplicatas
        if (usuarioRepository.findByLogin("admin").isEmpty()) {
            System.out.println("Criando usuário 'admin' padrão...");

            // Cria a entidade Usuario
            Usuario adminUser = new Usuario();
            adminUser.setLogin("admin");
            // IMPORTANTE: A senha será "admin", mas o código a salva criptografada
            adminUser.setSenha(passwordEncoder.encode("admin"));
            adminUser.setTipo(TipoUsuario.ADMIN);

            // Cria a entidade Funcionario (que também é uma Pessoa)
            Funcionario adminFuncionario = new Funcionario();
            adminFuncionario.setNome("Administrador do Sistema");
            adminFuncionario.setTelefone("00000-0000");
            adminFuncionario.setCargo("ADMIN");

            // Associa o Usuario ao Funcionario
            adminFuncionario.setUsuario(adminUser);

            // Salva o Funcionario (JPA cuidará de salvar a Pessoa e o Usuario)
            funcionarioRepository.save(adminFuncionario);

            System.out.println("Usuário 'admin' criado com sucesso!");
        }
    }
}