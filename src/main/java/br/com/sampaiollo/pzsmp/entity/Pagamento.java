package br.com.sampaiollo.pzsmp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamento")
@Data
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pagamento;

    @Column(nullable = false)
    private LocalDateTime datapag;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoPagamento metodo;// Enum: PIX, CARTAO_CREDITO, etc. [cite: 200]

    @Column(name = "valor_pago", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPago;
    
    @Enumerated(EnumType.STRING)
    private StatusPagamento status; // Enum: PENDENTE, EFETUADO, etc.

    // Muitos pagamentos podem estar associados a um pedido
    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false) // FK para Pedido [cite: 199]
    @JsonBackReference("pedido-pagamentos")
    private Pedido pedido;
}
