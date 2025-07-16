package br.com.sampaiollo.pzsmp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "itempedido")
@Data
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_item;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false, columnDefinition = "NUMERIC(10,2)")
    private BigDecimal preco;

    // Muitos ItemPedidos pertencem a um Pedido
    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    @JsonBackReference("pedido-itens")
    private Pedido pedido;

    // Muitos ItemPedidos podem se referir ao mesmo Produto
    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;
}
