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
    Cliente cliente = null;
    if (pedidoDto.getIdCliente() != null) {
        cliente = clienteRepository.findById(pedidoDto.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + pedidoDto.getIdCliente()));
    }

    Pedido pedido = new Pedido();
    pedido.setCliente(cliente);
    pedido.setData(LocalDateTime.now());
    pedido.setStatus(StatusPedido.PREPARANDO);
    
    // Define o nome temporário se ele foi enviado
    pedido.setNomeClienteTemporario(pedidoDto.getNomeClienteTemporario());

    if (pedidoDto.getIdMesa() != null) {
        Mesa mesa = mesaRepository.findById(pedidoDto.getIdMesa())
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada com número: " + pedidoDto.getIdMesa()));
        pedido.setMesa(mesa);
        mesa.setStatus(StatusMesa.OCUPADA);
        mesaRepository.save(mesa);
    }

    // (O resto da lógica para calcular o total e adicionar os itens continua a mesma)
    // ...
    
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
    // Define os status que consideramos como "não ativos"
    List<StatusPedido> statusesExcluidos = List.of(StatusPedido.ENTREGUE, StatusPedido.CANCELADO);
    
    // Busca no repositório todos os pedidos da mesa que NÃO estejam com os status da lista acima
    List<Pedido> pedidosAtivos = pedidoRepository.findByMesaNumeroAndStatusNotIn(numeroMesa, statusesExcluidos);
    
    // Converte a lista de entidades para uma lista de DTOs e a retorna
    return pedidosAtivos.stream()
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

// Dentro da classe PedidoService.java

@Transactional
public PedidoResponseDto fecharPedidoMesa(Integer pedidoId) {
    // 1. Busca o pedido existente
    Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + pedidoId));

    // 2. Altera o status do pedido para ENTREGUE (ou um novo status como FECHADO, se preferir)
    pedido.setStatus(StatusPedido.ENTREGUE);
    
    // 3. Pega a mesa associada a este pedido
    Mesa mesa = pedido.getMesa();

    // 4. Se o pedido estava associado a uma mesa, verifica se a mesa pode ser liberada
    if (mesa != null) {
        // Conta quantos outros pedidos ATIVOS (não cancelados ou entregues) ainda existem para esta mesa
        long pedidosAtivosNaMesa = pedidoRepository.findAll().stream()
                .filter(p -> mesa.equals(p.getMesa()) && 
                             p.getStatus() != StatusPedido.ENTREGUE &&
                             p.getStatus() != StatusPedido.CANCELADO)
                .count();

        // Se não houver mais nenhum pedido ativo, a mesa pode ser liberada
        if (pedidosAtivosNaMesa == 0) {
            mesa.setStatus(StatusMesa.LIVRE);
            mesaRepository.save(mesa);
        }
    }

    Pedido pedidoSalvo = pedidoRepository.save(pedido);
    return new PedidoResponseDto(pedidoSalvo);
}
    @Transactional
public void fecharCaixa() {
    // 1. Apaga todos os registros da tabela de pedidos.
    // Graças à configuração de cascata, os itens de pedido e pagamentos associados
    // também serão removidos.
    pedidoRepository.deleteAll();

    // 2. Busca todas as mesas cadastradas no sistema.
    List<Mesa> todasAsMesas = mesaRepository.findAll();

    // 3. Itera sobre cada mesa e define seu status como LIVRE.
    for (Mesa mesa : todasAsMesas) {
        mesa.setStatus(StatusMesa.LIVRE);
    }

    // 4. Salva a lista de mesas atualizada de volta no banco de dados.
    mesaRepository.saveAll(todasAsMesas);
}
}