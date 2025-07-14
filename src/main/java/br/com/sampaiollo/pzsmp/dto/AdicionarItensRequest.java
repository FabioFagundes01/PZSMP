package br.com.sampaiollo.pzsmp.dto;

import java.util.List;

// Este DTO representa uma lista de itens a serem adicionados a um pedido existente.
// Usamos o mesmo ItemPedidoDto que já tínhamos.
public record AdicionarItensRequest(
    List<ItemPedidoDto> itens
) {}