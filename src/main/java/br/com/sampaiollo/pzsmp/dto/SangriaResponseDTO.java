package br.com.sampaiollo.pzsmp.dto;

import br.com.sampaiollo.pzsmp.entity.Sangria;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// DTO para formatar os dados de sangria que serão enviados ao frontend
public record SangriaResponseDTO(
    Long id,
    LocalDateTime data,
    BigDecimal valor,
    String observacao,
    String nomeFuncionario
) {
    // Construtor para facilitar a conversão da Entidade para o DTO
    public SangriaResponseDTO(Sangria sangria) {
        this(
            sangria.getId(),
            sangria.getData(),
            sangria.getValor(),
            sangria.getObservacao(),
            sangria.getFuncionario().getNome() // Pega o nome do funcionário associado
        );
    }
}