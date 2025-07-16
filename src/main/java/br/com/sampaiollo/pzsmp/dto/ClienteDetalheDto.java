package br.com.sampaiollo.pzsmp.dto;

import br.com.sampaiollo.pzsmp.entity.Cliente;

/**
 * DTO para transportar os detalhes de um cliente, incluindo seu endereço principal.
 * A principal mudança é que agora ele contém um objeto EnderecoDto aninhado,
 * permitindo que o frontend acesse cada campo do endereço separadamente.
 */
public record ClienteDetalheDto(
    Integer id,
    String nome,
    String email,
    String telefone,
    EnderecoDto endereco // Objeto de endereço aninhado
) {
    /**
     * Construtor que transforma uma entidade Cliente neste DTO.
     * @param cliente A entidade Cliente vinda do banco de dados.
     */
    public ClienteDetalheDto(Cliente cliente) {
        this(
            cliente.getId(),
            cliente.getNome(),
            cliente.getEmail(),
            cliente.getTelefone(),
            // Lógica para criar o DTO de endereço:
            // Se a lista de endereços do cliente não for nula e não estiver vazia,
            // cria um EnderecoDto a partir do primeiro endereço da lista.
            // Caso contrário, o campo 'endereco' neste DTO será nulo.
            (cliente.getEnderecos() != null && !cliente.getEnderecos().isEmpty())
                ? new EnderecoDto(cliente.getEnderecos().get(0))
                : null
        );
    }
}
