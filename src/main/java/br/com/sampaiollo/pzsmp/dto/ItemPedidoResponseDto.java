package br.com.sampaiollo.pzsmp.dto;

import br.com.sampaiollo.pzsmp.entity.ItemPedido;
import java.math.BigDecimal;

public record ItemPedidoResponseDto(
        String nomeProduto,
        int quantidade,
        BigDecimal precoUnitario
) {
    // Construtor que converte a entidade para o DTO
    public ItemPedidoResponseDto(ItemPedido item) {
        this(item.getProduto().getNome(), item.getQuantidade(), item.getPreco());
    }
}