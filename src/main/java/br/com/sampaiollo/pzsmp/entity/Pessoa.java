package br.com.sampaiollo.pzsmp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pessoa")
@Inheritance(strategy = InheritanceType.JOINED) // Use a estratégia JOINED
@Data
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    private String telefone;
}