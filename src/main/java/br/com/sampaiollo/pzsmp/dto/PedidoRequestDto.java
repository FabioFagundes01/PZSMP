package br.com.sampaiollo.pzsmp.dto;

import lombok.Data;
import java.util.List;

@Data
public class PedidoRequestDto {
    private Integer idCliente;
    private Integer idMesa; // Opcional, para pedidos feitos na mesa
    String nomeClienteTemporario;
    private List<ItemPedidoDto> itens;
}