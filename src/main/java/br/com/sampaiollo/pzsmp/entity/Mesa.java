package br.com.sampaiollo.pzsmp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "mesa")
@Data
public class Mesa {

    @Id // A PK é o número da mesa, que não é auto-gerado.
    private Integer numero;

    private Integer capacidade;

    // Uma mesa pode ter várias reservas
    @OneToMany(mappedBy = "mesa")
    private List<Reserva> reservas;
    
    // Uma mesa pode ter vários pedidos feitos nela
    @OneToMany(mappedBy = "mesa")
    private List<Pedido> pedidos;
}
