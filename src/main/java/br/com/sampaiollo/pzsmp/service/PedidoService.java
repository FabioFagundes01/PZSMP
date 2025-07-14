package br.com.sampaiollo.pzsmp.service;

import br.com.sampaiollo.pzsmp.dto.AdicionarItensRequest;
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
import java.util.stream.Collectors;
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
        // Inicia o cliente como nulo
        Cliente cliente = null;
        
        // Se um ID de cliente foi fornecido, busca ele no banco
        if (pedidoDto.getIdCliente() != null) {
            cliente = clienteRepository.findById(pedidoDto.getIdCliente())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + pedidoDto.getIdCliente()));
        }

        // Cria o objeto Pedido principal
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente); // Define o cliente (pode ser nulo)
        pedido.setData(LocalDateTime.now());
        pedido.setStatus(StatusPedido.PREPARANDO);

        // Associa o pedido à mesa, se um ID de mesa foi fornecido
        if (pedidoDto.getIdMesa() != null) {
            Mesa mesa = mesaRepository.findById(pedidoDto.getIdMesa())
                    .orElseThrow(() -> new RuntimeException("Mesa não encontrada com número: " + pedidoDto.getIdMesa()));
            pedido.setMesa(mesa);
            // Atualiza o status da mesa para OCUPADA
            mesa.setStatus(StatusMesa.OCUPADA);
            mesaRepository.save(mesa);
        }

        // (O resto da lógica para calcular o total e adicionar os itens continua a mesma)
        BigDecimal totalPedido = BigDecimal.ZERO;
        List<ItemPedido> itensDoPedido = new ArrayList<>();

        for (var itemDto : pedidoDto.getItens()) {
            Produto produto = produtoRepository.findById(itemDto.getIdProduto())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + itemDto.getIdProduto()));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(itemDto.getQuantidade());
            itemPedido.setPreco(produto.getPreco());
            itemPedido.setPedido(pedido);

            itensDoPedido.add(itemPedido);
            totalPedido = totalPedido.add(produto.getPreco().multiply(BigDecimal.valueOf(itemDto.getQuantidade())));
        }

        pedido.setItens(itensDoPedido);
        pedido.setTotal(totalPedido);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

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
    public List<PedidoResponseDto> buscarPorMesa(Integer numeroMesa) {
    // Busca no repositório todos os pedidos da mesa que não estejam com status CANCELADO
    List<Pedido> pedidos = pedidoRepository.findByMesaNumeroAndStatusNot(numeroMesa, StatusPedido.CANCELADO);
    
    // Converte a lista de entidades para uma lista de DTOs e a retorna
    return pedidos.stream()
            .map(PedidoResponseDto::new)
            .collect(Collectors.toList());
}
    @Transactional
public PedidoResponseDto adicionarItens(Integer pedidoId, AdicionarItensRequest request) {
    // 1. Busca o pedido existente ou lança um erro
    Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + pedidoId));

    // 2. Itera sobre os novos itens para adicioná-los ao pedido
    for (var itemDto : request.itens()) {
        Produto produto = produtoRepository.findById(itemDto.getIdProduto())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + itemDto.getIdProduto()));

        ItemPedido novoItem = new ItemPedido();
        novoItem.setProduto(produto);
        novoItem.setQuantidade(itemDto.getQuantidade());
        novoItem.setPreco(produto.getPreco());
        novoItem.setPedido(pedido); // Associa o novo item ao pedido existente

        // Adiciona o novo item à lista de itens do pedido
        pedido.getItens().add(novoItem);

        // Atualiza o valor total do pedido
        BigDecimal valorAdicional = produto.getPreco().multiply(BigDecimal.valueOf(itemDto.getQuantidade()));
        pedido.setTotal(pedido.getTotal().add(valorAdicional));
    }

    // 3. Salva o pedido modificado
    Pedido pedidoSalvo = pedidoRepository.save(pedido);

    // 4. Retorna o DTO do pedido completo e atualizado
    return new PedidoResponseDto(pedidoSalvo);
}
}