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
        String nomeClienteTemporario,
        List<ItemPedidoResponseDto> itens
) {
    public PedidoResponseDto(Pedido pedido) {
        this(
                pedido.getId(),
                pedido.getData(),
                pedido.getStatus(),
                pedido.getTotal(),
                pedido.getCliente() != null ? new ClienteResponseDto(pedido.getCliente()) : null,
                pedido.getMesa() != null ? pedido.getMesa().getNumero() : null,
                pedido.getNomeClienteTemporario(), // <-- CAMPO ADICIONADO
                pedido.getItens().stream()
                        .map(ItemPedidoResponseDto::new)
                        .collect(Collectors.toList())
        );
    }
}
