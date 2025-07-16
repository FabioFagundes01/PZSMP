package br.com.sampaiollo.pzsmp.dto;

import br.com.sampaiollo.pzsmp.entity.Endereco;

// DTO para representar os dados de um endereço
    public record EnderecoDto(
    String rua,
    String bairro,
    Integer numero,
    String cidade,
    String cep
) {
    // Construtor que converte uma entidade Endereco neste DTO
    public EnderecoDto(Endereco endereco) {
        this(
            endereco.getRua(),
            endereco.getBairro(),
            endereco.getNumero(),
            endereco.getCidade(),
            endereco.getCep()
        );
    }
}
