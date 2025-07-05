package br.com.sampaiollo.pzsmp.service;

import br.com.sampaiollo.pzsmp.dto.FuncionarioRequestDto;
import br.com.sampaiollo.pzsmp.entity.Funcionario;
import br.com.sampaiollo.pzsmp.entity.TipoUsuario;
import br.com.sampaiollo.pzsmp.entity.Usuario;
import br.com.sampaiollo.pzsmp.repository.FuncionarioRepository;
import br.com.sampaiollo.pzsmp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder; // IMPORTANTE!

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injeta o codificador

    @Transactional
    public Funcionario cadastrarFuncionario(FuncionarioRequestDto dto) {
        if (usuarioRepository.findByLogin(dto.getLogin()).isPresent()) {
            throw new RuntimeException("Login de usuário já existe: " + dto.getLogin());
        }
        
        // Criptografa a senha antes de salvar!
        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());
        
        Usuario novoUsuario = new Usuario();
        novoUsuario.setLogin(dto.getLogin());
        novoUsuario.setSenha(senhaCriptografada); // Salva a senha criptografada
        novoUsuario.setTipo(TipoUsuario.FUNCIONARIO);

        Funcionario funcionario = new Funcionario();
        // ... setar outros campos do funcionário
        funcionario.setUsuario(novoUsuario);

        return funcionarioRepository.save(funcionario);
    }
    
    public List<Funcionario> listarTodos() {
        return funcionarioRepository.findAll();
    }

    public Optional<Funcionario> buscarPorId(Integer id) {
        return funcionarioRepository.findById(id);
    }
}