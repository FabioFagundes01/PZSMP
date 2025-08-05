package br.com.sampaiollo.pzsmp.service;

import br.com.sampaiollo.pzsmp.dto.SangriaRequest;
import br.com.sampaiollo.pzsmp.entity.Funcionario;
import br.com.sampaiollo.pzsmp.entity.Sangria;
import br.com.sampaiollo.pzsmp.entity.Usuario;
import br.com.sampaiollo.pzsmp.repository.FuncionarioRepository;
import br.com.sampaiollo.pzsmp.repository.SangriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.sampaiollo.pzsmp.dto.SangriaResponseDTO; // <<< IMPORTE O NOVO DTO
import java.util.List; // <<< IMPORTE LIST
import java.util.stream.Collectors;

import java.time.LocalDateTime;

@Service
public class CaixaService {

    @Autowired
    private SangriaRepository sangriaRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Transactional
    public Sangria realizarSangria(SangriaRequest request) {
        // Pega o usuário que está logado no momento
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        // Encontra o funcionário correspondente a esse usuário
        Funcionario funcionario = funcionarioRepository.findByUsuarioLogin(usuarioLogado.getLogin())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado para o usuário logado."));

        // Cria a nova entidade Sangria
        Sangria novaSangria = new Sangria();
        novaSangria.setFuncionario(funcionario);
        novaSangria.setValor(request.valor());
        novaSangria.setObservacao(request.observacao());
        novaSangria.setData(LocalDateTime.now());

        // Salva o registo no banco de dados
        return sangriaRepository.save(novaSangria);
    }
    
    @Transactional(readOnly = true)
    public List<SangriaResponseDTO> listarTodasSangrias() {
        return sangriaRepository.findAll() // Busca todos os registros
                .stream()                  // Converte para um stream
                .map(SangriaResponseDTO::new) // Mapeia cada Sangria para um SangriaResponseDTO
                .collect(Collectors.toList()); // Coleta em uma lista
    }
}
