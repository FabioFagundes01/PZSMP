package br.com.sampaiollo.pzsmp.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FuncionarioRequestDto {
    private String nome;
    private String telefone;
    private String cargo;
    private BigDecimal salario;
    private String login;
    private String senha;
}