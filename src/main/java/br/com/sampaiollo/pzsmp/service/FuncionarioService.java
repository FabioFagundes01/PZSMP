package br.com.sampaiollo.pzsmp.service;

import br.com.sampaiollo.pzsmp.dto.FuncionarioRequestDto;
import br.com.sampaiollo.pzsmp.dto.FuncionarioResponseDto;
import br.com.sampaiollo.pzsmp.entity.Funcionario;
import br.com.sampaiollo.pzsmp.entity.TipoUsuario;
import br.com.sampaiollo.pzsmp.entity.Usuario;
import br.com.sampaiollo.pzsmp.exception.BusinessException; // Importa a nova exceção
import br.com.sampaiollo.pzsmp.repository.FuncionarioRepository;
import br.com.sampaiollo.pzsmp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public FuncionarioResponseDto cadastrarFuncionario(FuncionarioRequestDto dto) {
        // Verifica se o login já está em uso
        if (usuarioRepository.findByLogin(dto.getLogin()).isPresent()) {
            // ATUALIZAÇÃO: Usando a exceção customizada para um erro de negócio
            throw new BusinessException("Login de usuário já existe: " + dto.getLogin());
        }

        // Criptografa a senha do novo usuário
        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());

        // Cria a entidade Usuario
        Usuario novoUsuario = new Usuario();
        novoUsuario.setLogin(dto.getLogin());
        novoUsuario.setSenha(senhaCriptografada);
        novoUsuario.setTipo(TipoUsuario.FUNCIONARIO);

        // Cria a entidade Funcionario com todos os dados do DTO
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());
        funcionario.setTelefone(dto.getTelefone());
        funcionario.setCargo(dto.getCargo());
        funcionario.setUsuario(novoUsuario);

        // Salva o novo funcionário (o usuário será salvo em cascata)
        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);

        // Retorna o DTO de resposta, e não a entidade
        return new FuncionarioResponseDto(funcionarioSalvo);
    }

    /**
     * Lista todos os funcionários cadastrados, já convertidos para o DTO de resposta.
     * @return Uma lista de FuncionarioResponseDto.
     */
    public List<FuncionarioResponseDto> listarTodos() {
        return funcionarioRepository.findAll()
                .stream()
                .map(FuncionarioResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca um funcionário específico pelo seu ID.
     * @param id O ID do funcionário.
     * @return um Optional contendo o FuncionarioResponseDto se encontrado.
     */
    public Optional<FuncionarioResponseDto> buscarPorId(Integer id) {
        return funcionarioRepository.findById(id)
                .map(FuncionarioResponseDto::new);
    }
}