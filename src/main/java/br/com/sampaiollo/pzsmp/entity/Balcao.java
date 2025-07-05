package br.com.sampaiollo.pzsmp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "balcao")
@Data
public class Balcao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_balcao;

    @Column(length = 100)
    private String status;// Ex: "Disponível", "Ocupado" [cite: 183]

    // Um balcão pode ter vários pedidos associados (opcional, para navegação)
    @OneToMany(mappedBy = "balcao")
    private List<Pedido> pedidos;
}