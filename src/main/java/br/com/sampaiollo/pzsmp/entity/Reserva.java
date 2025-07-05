package br.com.sampaiollo.pzsmp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reserva")
@Data
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_reserva;

    @Column(nullable = false)
    private LocalDateTime dataReserva;

    @Column(name = "num_pessoas", nullable = false)
    private Integer numPessoas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusReserva status;// Enum: PENDENTE, CONFIRMADA, etc. [cite: 211]

    @Column(length = 200)
    private String observacoes;

    // Muitas reservas podem ser de um cliente
    @ManyToOne
    @JoinColumn(name = "id_pessoa_cliente", nullable = false) // FK para Cliente [cite: 211]
    private Cliente cliente;

    // Muitas reservas podem ser para uma mesa
    @ManyToOne
    @JoinColumn(name = "id_mesa", nullable = false) // FK para Mesa [cite: 211]
    private Mesa mesa;
}
