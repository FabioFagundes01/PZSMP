package br.com.sampaiollo.pzsmp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "mesa")
@Data
public class Mesa {

    @Id
    private Integer numero;

    private Integer capacidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusMesa status; // CAMPO ATUALIZADO/ADICIONADO

    @OneToMany(mappedBy = "mesa")
    private List<Reserva> reservas;
    
    @OneToMany(mappedBy = "mesa")
    private List<Pedido> pedidos;
}