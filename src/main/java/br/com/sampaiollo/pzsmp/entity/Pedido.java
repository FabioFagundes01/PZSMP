package br.com.sampaiollo.pzsmp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedido")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "NUMERIC(10,2)")
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status;

    @Column(name = "data", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime data;
    
    @Column(nullable = false)
    private boolean pago = false; // CAMPO ATUALIZADO/ADICIONADO

    @ManyToOne
    @JoinColumn(name = "id_pessoa", nullable = true)
    @JsonBackReference("cliente-pedidos")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    @JsonManagedReference("pedido-itens")
    private List<ItemPedido> itens;
    
    @ManyToOne
    @JoinColumn(name = "id_mesa")
    @JsonBackReference("mesa-pedidos")
    private Mesa mesa;
    
    @ManyToOne
    @JoinColumn(name = "id_balcao")
    private Balcao balcao;
}