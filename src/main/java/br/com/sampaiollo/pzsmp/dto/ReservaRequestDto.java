package br.com.sampaiollo.pzsmp.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object para criar uma nova reserva.
 * Simplificado para aceitar um nome em vez de um ID de cliente.
 */
public record ReservaRequestDto(
    Integer idMesa,
    String nomeReserva,
    Integer numPessoas,
    LocalDateTime dataReserva,
    String observacoes
) {}