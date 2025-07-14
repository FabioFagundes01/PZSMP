package br.com.sampaiollo.pzsmp.dto;

public record MesaStatusResponse(
    Integer numero,
    Integer capacidade,
    String status
) {}