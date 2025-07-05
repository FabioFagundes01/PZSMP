package br.com.sampaiollo.pzsmp.dto;

import lombok.Data;

@Data
public class EnderecoRequestDto {
    private String rua;
    private String bairro;
    private Integer numero;
    private String cidade;
    private String cep;
}