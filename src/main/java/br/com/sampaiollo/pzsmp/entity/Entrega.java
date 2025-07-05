package br.com.sampaiollo.pzsmp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "entrega")
@Data
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_entrega;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEntrega status;// Enum: EM_ROTA, ENTREGUE [cite: 189]

    // Uma entrega é para exatamente um pedido
    @OneToOne
    @JoinColumn(name = "id_pedido", nullable = false, unique = true) // FK para Pedido [cite: 189]
    private Pedido pedido;

    // Muitas entregas podem ser feitas por um funcionário
    @ManyToOne
    @JoinColumn(name = "id_funcionario_entregador") // FK para Funcionario [cite: 220]
    private Funcionario funcionarioEntregador;

    // Muitas entregas podem ir para o mesmo endereço, mas esta entrega vai para um específico
    @ManyToOne
    @JoinColumn(name = "id_endereco_entrega") // FK para Endereco [cite: 220]
    private Endereco endereco;
}
