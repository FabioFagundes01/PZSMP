package br.com.sampaiollo.pzsmp.controller;

import br.com.sampaiollo.pzsmp.dto.PedidoRequestDto;
import br.com.sampaiollo.pzsmp.dto.PedidoResponseDto;
import br.com.sampaiollo.pzsmp.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    /**
     * Endpoint para realizar um novo pedido.
     * Recebe um PedidoRequestDto e retorna um PedidoResponseDto.
     */
    @PostMapping
    public ResponseEntity<PedidoResponseDto> realizarPedido(@RequestBody PedidoRequestDto pedidoDto) {
        PedidoResponseDto novoPedido = pedidoService.realizarPedido(pedidoDto);
        return ResponseEntity.status(201).body(novoPedido);
    }

    /**
     * Endpoint para buscar todos os pedidos.
     * Retorna uma lista de PedidoResponseDto.
     */
    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> listarTodosPedidos() {
        List<PedidoResponseDto> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

    /**
     * Endpoint para buscar um pedido específico pelo seu ID.
     * Retorna um PedidoResponseDto ou 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> buscarPedidoPorId(@PathVariable Integer id) {
        return pedidoService.buscarPorId(id)
                .map(pedidoDto -> ResponseEntity.ok(pedidoDto))
                .orElse(ResponseEntity.notFound().build());
    }
}