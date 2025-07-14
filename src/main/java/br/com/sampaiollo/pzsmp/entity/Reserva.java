package br.com.sampaiollo.pzsmp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Entity
@Table(name = "reserva")
@Data
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime dataReserva;

    @Column(nullable = false)
    private Integer numPessoas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusReserva status;
    
    private String observacoes;

    // NOVO CAMPO: para armazenar o nome temporário da reserva
    private String nomeReserva;

    @ManyToOne
    // A coluna 'id_pessoa' agora pode ser nula, tornando a associação com Cliente opcional.
    @JoinColumn(name = "id_pessoa", nullable = true) 
    @JsonBackReference
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_mesa", nullable = false)
    @JsonBackReference
    private Mesa mesa;
}