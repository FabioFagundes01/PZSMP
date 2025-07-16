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
        Integer numeroMesa, // <<< CAMPO ADICIONADO AQUI
        List<ItemPedidoResponseDto> itens
) {
    public PedidoResponseDto(Pedido pedido) {
        this(
                pedido.getId(),
                pedido.getData(),
                pedido.getStatus(),
                pedido.getTotal(),
                pedido.getCliente() != null ? new ClienteResponseDto(pedido.getCliente()) : null,
                // Se a mesa do pedido não for nula, pega o número dela. Senão, o campo fica nulo.
                pedido.getMesa() != null ? pedido.getMesa().getNumero() : null, // <<< LÓGICA ADICIONADA AQUI
                pedido.getItens().stream()
                        .map(ItemPedidoResponseDto::new)
                        .collect(Collectors.toList())
        );
    }
}
