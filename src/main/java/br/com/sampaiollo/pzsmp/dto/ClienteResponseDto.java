package br.com.sampaiollo.pzsmp.dto;

import br.com.sampaiollo.pzsmp.entity.Cliente;

public record ClienteResponseDto(
        Integer id,
        String nome
) {
    // Construtor que converte a entidade para o DTO
    public ClienteResponseDto(Cliente cliente) {
        this(cliente.getId(), cliente.getNome());
    }
}