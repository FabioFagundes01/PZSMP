package br.com.sampaiollo.pzsmp.dto;

import lombok.Data;

@Data
public class ClienteRequestDto {
    private String nome;
    private String telefone;
    private String email;
    // A senha será gerenciada pelo sistema ou em um DTO de "criação de conta" mais específico
    // Novos campos para o Endereço
    private String rua;
    private String bairro;
    private Integer numero;
    private String cidade;
    private String cep;
}