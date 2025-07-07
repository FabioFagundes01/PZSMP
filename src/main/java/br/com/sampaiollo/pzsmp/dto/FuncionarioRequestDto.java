package br.com.sampaiollo.pzsmp.dto;

import lombok.Data;

@Data
public class FuncionarioRequestDto {

    private String nome;
    private String telefone;
    private String cargo;
    private String login;
    private String senha;
}