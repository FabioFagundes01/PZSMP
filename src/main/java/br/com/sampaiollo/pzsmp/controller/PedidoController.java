package br.com.sampaiollo.pzsmp.controller;

import br.com.sampaiollo.pzsmp.dto.AdicionarItensRequest;
import br.com.sampaiollo.pzsmp.dto.PedidoRequestDto;
import br.com.sampaiollo.pzsmp.dto.PedidoResponseDto;
import br.com.sampaiollo.pzsmp.dto.UpdateStatusRequest;
import br.com.sampaiollo.pzsmp.entity.RelatorioDiario;
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
    
    @PutMapping("/{id}/status")
public ResponseEntity<PedidoResponseDto> atualizarStatus(
        @PathVariable Integer id,
        @RequestBody UpdateStatusRequest request) {

    PedidoResponseDto pedidoAtualizado = pedidoService.atualizarStatus(id, request.novoStatus());
    return ResponseEntity.ok(pedidoAtualizado);
}

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
    
    @GetMapping("/mesa/{numero}")
    public ResponseEntity<List<PedidoResponseDto>> buscarPedidosPorMesa(@PathVariable Integer numero) {
        List<PedidoResponseDto> pedidos = pedidoService.buscarPorMesa(numero);
        return ResponseEntity.ok(pedidos);
    }
    
    @PostMapping("/{id}/itens")
public ResponseEntity<PedidoResponseDto> adicionarItens(
        @PathVariable Integer id,
        @RequestBody AdicionarItensRequest request) {
            
    PedidoResponseDto pedidoAtualizado = pedidoService.adicionarItens(id, request);
    return ResponseEntity.ok(pedidoAtualizado);
}

@PutMapping("/{id}/fechar")
public ResponseEntity<PedidoResponseDto> fecharPedidoMesa(@PathVariable Integer id) {
    PedidoResponseDto pedidoFechado = pedidoService.fecharPedidoMesa(id);
    return ResponseEntity.ok(pedidoFechado);
}
@DeleteMapping("/fechar-caixa")
public ResponseEntity<Void> fecharCaixa() {
    pedidoService.fecharCaixa();
    return ResponseEntity.noContent().build(); // Retorna 204 No Content (sucesso sem corpo)
}

@GetMapping("/relatorios")
public ResponseEntity<List<RelatorioDiario>> listarRelatorios() {
    return ResponseEntity.ok(pedidoService.listarRelatorios());
}
}