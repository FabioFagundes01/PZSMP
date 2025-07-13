package br.com.sampaiollo.pzsmp.service;

import br.com.sampaiollo.pzsmp.dto.PedidoRequestDto;
import br.com.sampaiollo.pzsmp.dto.PedidoResponseDto;
import br.com.sampaiollo.pzsmp.entity.*;
import br.com.sampaiollo.pzsmp.repository.ClienteRepository;
import br.com.sampaiollo.pzsmp.repository.MesaRepository;
import br.com.sampaiollo.pzsmp.repository.PedidoRepository;
import br.com.sampaiollo.pzsmp.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private MesaRepository mesaRepository;

    @Transactional
    public PedidoResponseDto realizarPedido(PedidoRequestDto pedidoDto) {
        // Busca o cliente ou lança uma exceção (que será capturada pelo RestExceptionHandler)
        Cliente cliente = clienteRepository.findById(pedidoDto.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + pedidoDto.getIdCliente()));

        // Cria o objeto Pedido principal
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setData(LocalDateTime.now());
        pedido.setStatus(StatusPedido.PREPARANDO);

        // Se um ID de mesa foi fornecido, busca e associa o pedido à mesa
        if (pedidoDto.getIdMesa() != null) {
            Mesa mesa = mesaRepository.findById(pedidoDto.getIdMesa())
                    .orElseThrow(() -> new RuntimeException("Mesa não encontrada com número: " + pedidoDto.getIdMesa()));
            pedido.setMesa(mesa);
        }

        BigDecimal totalPedido = BigDecimal.ZERO;
        List<ItemPedido> itensDoPedido = new ArrayList<>();

        // Itera sobre os itens do DTO para criar as entidades ItemPedido
        for (var itemDto : pedidoDto.getItens()) {
            Produto produto = produtoRepository.findById(itemDto.getIdProduto())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + itemDto.getIdProduto()));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(itemDto.getQuantidade());
            itemPedido.setPreco(produto.getPreco()); // Pega o preço atual do produto
            itemPedido.setPedido(pedido); // Associa este item ao pedido principal

            itensDoPedido.add(itemPedido);

            // Soma ao total do pedido
            totalPedido = totalPedido.add(produto.getPreco().multiply(BigDecimal.valueOf(itemDto.getQuantidade())));
        }

        // Atribui a lista de itens e o total calculado ao pedido
        pedido.setItens(itensDoPedido);
        pedido.setTotal(totalPedido);

        // Salva o pedido. Graças ao Cascade, os ItemPedido também serão salvos.
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        // Converte a entidade salva para o DTO de resposta e a retorna
        return new PedidoResponseDto(pedidoSalvo);
    }
    
    @Transactional
public PedidoResponseDto atualizarStatus(Integer pedidoId, String novoStatus) {
    // 1. Encontra o pedido no banco de dados
    Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + pedidoId));

    // 2. Converte a String do novo status para o tipo Enum
    StatusPedido statusEnum = StatusPedido.valueOf(novoStatus.toUpperCase());

    // 3. Atualiza o status do pedido
    pedido.setStatus(statusEnum);

    // 4. Salva o pedido atualizado no banco
    Pedido pedidoSalvo = pedidoRepository.save(pedido);

    // 5. Retorna o DTO do pedido atualizado
    return new PedidoResponseDto(pedidoSalvo);
}

    public Optional<PedidoResponseDto> buscarPorId(Integer id) {
        return pedidoRepository.findById(id)
                .map(PedidoResponseDto::new); // Converte o Pedido encontrado para PedidoResponseDto
    }

    public List<PedidoResponseDto> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(PedidoResponseDto::new) // Converte cada Pedido da lista para PedidoResponseDto
                .collect(Collectors.toList());
    }
}