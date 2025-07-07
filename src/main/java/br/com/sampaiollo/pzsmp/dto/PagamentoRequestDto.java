package br.com.sampaiollo.pzsmp.dto;

import br.com.sampaiollo.pzsmp.entity.MetodoPagamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record PagamentoRequestDto(
    @NotNull Integer idPedido,
    @NotNull MetodoPagamento metodo,
    @NotNull @Positive BigDecimal valorPago
) {}