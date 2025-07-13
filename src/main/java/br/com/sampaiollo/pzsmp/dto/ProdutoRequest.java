
package br.com.sampaiollo.pzsmp.dto;

import java.math.BigDecimal;

public record ProdutoRequest(
    String nome,
    String tipo,
    BigDecimal preco
) {}