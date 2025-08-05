package br.com.sampaiollo.pzsmp.dto;

import java.math.BigDecimal;

public record SangriaRequest(BigDecimal valor, String observacao) {}