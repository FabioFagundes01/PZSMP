package br.com.sampaiollo.pzsmp.controller;

import br.com.sampaiollo.pzsmp.dto.ClienteRequestDto;
import br.com.sampaiollo.pzsmp.dto.EnderecoRequestDto;
import br.com.sampaiollo.pzsmp.entity.Cliente;
import br.com.sampaiollo.pzsmp.entity.Endereco;
import br.com.sampaiollo.pzsmp.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.sampaiollo.pzsmp.dto.ClienteDetalheDto;

import java.util.List;

@RestController
@RequestMapping("/api/clientes") // Define o URL base para todos os métodos deste controller
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem (bom para desenvolvimento)
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Endpoint para criar um novo cliente
    // HTTP POST -> http://localhost:8080/api/clientes
    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@RequestBody ClienteRequestDto clienteDto) {
        Cliente novoCliente = clienteService.criarCliente(clienteDto);
        return ResponseEntity.status(201).body(novoCliente); // 201 Created
    }

    // Endpoint para buscar todos os clientes
    // HTTP GET -> http://localhost:8080/api/clientes
    @GetMapping
    public ResponseEntity<List<ClienteDetalheDto>> listarTodos() {
        List<ClienteDetalheDto> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }

    // Endpoint para buscar um cliente por ID
    // HTTP GET -> http://localhost:8080/api/clientes/1
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id) {
        return clienteService.buscarPorId(id)
                .map(cliente -> ResponseEntity.ok(cliente)) // Se encontrou, retorna 200 OK com o cliente
                .orElse(ResponseEntity.notFound().build()); // Se não, retorna 404 Not Found
    }

    // Endpoint para adicionar um endereço a um cliente
    // HTTP POST -> http://localhost:8080/api/clientes/1/enderecos
    @PostMapping("/{clienteId}/enderecos")
    public ResponseEntity<Endereco> adicionarEndereco(@PathVariable Integer clienteId, @RequestBody EnderecoRequestDto enderecoDto) {
        Endereco novoEndereco = clienteService.adicionarEndereco(clienteId, enderecoDto);
        return ResponseEntity.status(201).body(novoEndereco);
    }
    
    @PutMapping("/{id}")
public ResponseEntity<Cliente> atualizarCliente(@PathVariable Integer id, @RequestBody ClienteRequestDto clienteDto) {
    Cliente clienteAtualizado = clienteService.atualizarCliente(id, clienteDto);
    return ResponseEntity.ok(clienteAtualizado);
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> excluirCliente(@PathVariable Integer id) {
    clienteService.excluirCliente(id);
    return ResponseEntity.noContent().build(); // Retorna 204 No Content
}
}