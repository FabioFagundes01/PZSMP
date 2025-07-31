package br.com.sampaiollo.pzsmp.service;

import br.com.sampaiollo.pzsmp.dto.ClienteRequestDto;
import br.com.sampaiollo.pzsmp.dto.EnderecoRequestDto;
import br.com.sampaiollo.pzsmp.entity.Cliente;
import br.com.sampaiollo.pzsmp.entity.Endereco;
import br.com.sampaiollo.pzsmp.repository.ClienteRepository;
import br.com.sampaiollo.pzsmp.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.sampaiollo.pzsmp.dto.ClienteDetalheDto;
import br.com.sampaiollo.pzsmp.repository.PessoaRepository;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Garanta que o EnderecoRepository está injetado
    @Autowired
    private EnderecoRepository enderecoRepository;
    
    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional
    public Cliente criarCliente(ClienteRequestDto dto) {
        // Validação para impedir e-mails duplicados
        if (clienteRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado: " + dto.getEmail());
        }

        // 1. Cria e salva a entidade Cliente
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEmail(dto.getEmail());
        Cliente novoClienteSalvo = clienteRepository.save(cliente);

        // 2. Verifica se foram fornecidos dados de endereço no formulário
        if (dto.getRua() != null && !dto.getRua().isEmpty()) {
            // Cria e salva a entidade Endereco, usando a sua classe Endereco.java
            Endereco endereco = new Endereco();
            endereco.setCliente(novoClienteSalvo); // Associa o endereço ao cliente recém-criado
            endereco.setRua(dto.getRua());
            endereco.setBairro(dto.getBairro());
            endereco.setNumero(dto.getNumero());
            endereco.setCidade(dto.getCidade());
            endereco.setCep(dto.getCep());
            
            enderecoRepository.save(endereco);
        }

        return novoClienteSalvo;
    }

    // O método de adicionar endereço avulso continua funcionando como antes
    @Transactional
    public Endereco adicionarEndereco(Integer clienteId, EnderecoRequestDto dto) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + clienteId));

        Endereco endereco = new Endereco();
        endereco.setCliente(cliente);
        endereco.setRua(dto.getRua());
        endereco.setBairro(dto.getBairro());
        endereco.setNumero(dto.getNumero());
        endereco.setCidade(dto.getCidade());
        endereco.setCep(dto.getCep());
        
        return enderecoRepository.save(endereco);
    }
    
    public Optional<Cliente> buscarPorId(Integer id) {
        return clienteRepository.findById(id);
    }
    
    public List<ClienteDetalheDto> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(ClienteDetalheDto::new) // Converte cada Cliente para ClienteDetalheDto
                .collect(Collectors.toList());
    }
    
    @Transactional
public Cliente atualizarCliente(Integer id, ClienteRequestDto dto) {
    // 1. Busca o cliente existente
    Cliente clienteExistente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));

    // 2. Atualiza os dados pessoais do cliente
    clienteExistente.setNome(dto.getNome());
    clienteExistente.setTelefone(dto.getTelefone());
    clienteExistente.setEmail(dto.getEmail());
    
    // 3. Atualiza o primeiro endereço do cliente (se ele tiver um)
    if (clienteExistente.getEnderecos() != null && !clienteExistente.getEnderecos().isEmpty()) {
        Endereco enderecoPrincipal = clienteExistente.getEnderecos().get(0);
        enderecoPrincipal.setRua(dto.getRua());
        enderecoPrincipal.setBairro(dto.getBairro());
        enderecoPrincipal.setNumero(dto.getNumero());
        enderecoPrincipal.setCidade(dto.getCidade());
        enderecoPrincipal.setCep(dto.getCep());
        enderecoRepository.save(enderecoPrincipal); // Salva as alterações no endereço
    }

    // 4. Salva as alterações no cliente
    return clienteRepository.save(clienteExistente);
}

@Transactional
public void excluirCliente(Integer id) {
    if (!clienteRepository.existsById(id)) {
        throw new RuntimeException("Cliente não encontrado com ID: " + id);
    }
    // Graças à configuração de cascata no banco, ao deletar a pessoa,
    // os registros em cliente, endereco, pedido, etc., serão removidos.
    pessoaRepository.deleteById(id); // Deleta a partir da tabela Pessoa
}

public List<ClienteDetalheDto> buscarClientes(String termo) {
    // Procura no repositório por nome ou telefone
    List<Cliente> clientesEncontrados = clienteRepository.findByNomeContainingIgnoreCaseOrTelefoneContainingIgnoreCase(termo, termo);
    
    // Converte a lista de entidades para a lista de DTOs
    return clientesEncontrados.stream()
            .map(ClienteDetalheDto::new)
            .collect(Collectors.toList());
}
}
