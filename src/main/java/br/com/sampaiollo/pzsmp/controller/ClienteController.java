package br.com.sampaiollo.pzsmp.controller;

import br.com.sampaiollo.pzsmp.dto.ClienteDetalheDto;
import br.com.sampaiollo.pzsmp.dto.ClienteRequestDto;
import br.com.sampaiollo.pzsmp.dto.EnderecoRequestDto;
import br.com.sampaiollo.pzsmp.entity.Cliente;
import br.com.sampaiollo.pzsmp.entity.Endereco;
import br.com.sampaiollo.pzsmp.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes") // Define o URL base para todos os métodos deste controller
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem (bom para desenvolvimento)
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /**
     * Endpoint para criar um novo cliente (com ou sem endereço).
     * HTTP POST -> http://localhost:8081/api/clientes
     */
    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@RequestBody ClienteRequestDto clienteDto) {
        Cliente novoCliente = clienteService.criarCliente(clienteDto);
        return ResponseEntity.status(201).body(novoCliente); // 201 Created
    }

    /**
     * Endpoint para buscar todos os clientes com os seus detalhes de endereço.
     * HTTP GET -> http://localhost:8081/api/clientes
     */
    @GetMapping
    public ResponseEntity<List<ClienteDetalheDto>> listarTodos() {
        List<ClienteDetalheDto> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes); // 200 OK
    }

    /**
     * Endpoint para buscar um cliente específico pelo seu ID.
     * HTTP GET -> http://localhost:8081/api/clientes/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id) {
        return clienteService.buscarPorId(id)
                .map(cliente -> ResponseEntity.ok(cliente)) // Se encontrou, retorna 200 OK com o cliente
                .orElse(ResponseEntity.notFound().build()); // Se não, retorna 404 Not Found
    }

    /**
     * Endpoint para buscar clientes por nome ou telefone.
     * Exemplo de uso: GET -> http://localhost:8081/api/clientes/buscar?termo=Fabio
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteDetalheDto>> buscarClientes(@RequestParam("termo") String termo) {
        List<ClienteDetalheDto> clientes = clienteService.buscarClientes(termo);
        return ResponseEntity.ok(clientes);
    }

    /**
     * Endpoint para atualizar os dados de um cliente existente.
     * HTTP PUT -> http://localhost:8081/api/clientes/1
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Integer id, @RequestBody ClienteRequestDto clienteDto) {
        Cliente clienteAtualizado = clienteService.atualizarCliente(id, clienteDto);
        return ResponseEntity.ok(clienteAtualizado);
    }

    /**
     * Endpoint para excluir um cliente.
     * HTTP DELETE -> http://localhost:8081/api/clientes/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Integer id) {
        clienteService.excluirCliente(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

    /**
     * Endpoint para adicionar um endereço avulso a um cliente já existente.
     * HTTP POST -> http://localhost:8081/api/clientes/1/enderecos
     */
    @PostMapping("/{clienteId}/enderecos")
    public ResponseEntity<Endereco> adicionarEndereco(@PathVariable Integer clienteId, @RequestBody EnderecoRequestDto enderecoDto) {
        Endereco novoEndereco = clienteService.adicionarEndereco(clienteId, enderecoDto);
        return ResponseEntity.status(201).body(novoEndereco);
    }
}
