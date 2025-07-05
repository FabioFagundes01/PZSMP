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

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public Cliente criarCliente(ClienteRequestDto dto) {
        // Validação para impedir e-mails duplicados
        if (clienteRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado: " + dto.getEmail());
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEmail(dto.getEmail());

        return clienteRepository.save(cliente);
    }

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
    
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
}