package br.com.sampaiollo.pzsmp.dto;

import br.com.sampaiollo.pzsmp.entity.Pedido;
import br.com.sampaiollo.pzsmp.entity.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record PedidoResponseDto(
        Integer idPedido,
        LocalDateTime data,
        StatusPedido status,
        BigDecimal total,
        ClienteResponseDto cliente,
        List<ItemPedidoResponseDto> itens
) {
    // Construtor que converte a entidade Pedido complexa neste DTO simples e seguro
    public PedidoResponseDto(Pedido pedido) {
        this(
                pedido.getId_pedido(),
                pedido.getData(),
                pedido.getStatus(),
                pedido.getTotal(),
                new ClienteResponseDto(pedido.getCliente()), // Converte o cliente aninhado
                pedido.getItens().stream()
                        .map(ItemPedidoResponseDto::new) // Converte cada item do pedido
                        .collect(Collectors.toList())
        );
    }
}