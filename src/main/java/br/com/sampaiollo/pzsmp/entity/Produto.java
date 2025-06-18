package br.com.sampaiollo.pzsmp.entity;

import jakarta.persistence.*;
import lombok.Data; // Do Lombok, para getters/setters automáticos

@Entity
@Table(name = "produto") 
@Data 
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_produto;

    @Column(nullable = false)
    private String nome;

    // Ainda não criamos o ENUM no Java, por enquanto podemos usar String
    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private Double preco;
}