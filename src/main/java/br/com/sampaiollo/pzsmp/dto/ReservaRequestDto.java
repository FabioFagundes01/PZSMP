package br.com.sampaiollo.pzsmp.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReservaRequestDto {
    private Integer idCliente;
    private Integer idMesa;
    private LocalDateTime dataReserva;
    private Integer numPessoas;
    private String observacoes;
}