package br.com.sampaiollo.pzsmp.entity;

import jakarta.persistence.*;
import lombok.Data; // A "mágica" acontece por causa desta linha
import java.math.BigDecimal;

@Entity
@Table(name = "produto")
@Data // Esta anotação cria os getters, setters e outros métodos para você
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_produto;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoProduto tipo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;
    
    private String imagemUrl;
}