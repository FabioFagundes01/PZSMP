package br.com.sampaiollo.pzsmp.dto;

import br.com.sampaiollo.pzsmp.entity.Funcionario;

public record FuncionarioResponseDto(
    Integer id,
    String nome,
    String telefone,
    String cargo,
    String login
) {
    // Construtor que converte a entidade para o DTO de forma segura
    public FuncionarioResponseDto(Funcionario funcionario) {
        this(
            funcionario.getId(),
            funcionario.getNome(),
            funcionario.getTelefone(),
            funcionario.getCargo(),
            funcionario.getUsuario().getLogin()
        );
    }
}