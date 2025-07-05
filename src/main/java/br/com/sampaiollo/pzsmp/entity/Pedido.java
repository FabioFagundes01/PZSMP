package br.com.sampaiollo.pzsmp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedido")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pedido;

    @Column(columnDefinition = "NUMERIC(10,2)")
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status;

    @Column(name = "data", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime data;

    // Muitos Pedidos podem ser feitos por um Cliente
    @ManyToOne
    @JoinColumn(name = "id_pessoa") // Esta é a coluna da chave estrangeira na tabela Pedido
    private Cliente cliente;

    // Um Pedido tem muitos ItemPedidos
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;
    
    // Um Pedido pode ser associado a uma Mesa (opcional)
    @ManyToOne
    @JoinColumn(name = "id_mesa")
    private Mesa mesa;
    
    @ManyToOne
    @JoinColumn(name = "id_balcao") // FK para Balcao
    private Balcao balcao;
}
