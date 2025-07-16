package br.com.sampaiollo.pzsmp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference; // <-- Importe esta classe
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "endereco")
@Data
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_endereco;

    @Column(nullable = false)
    private String rua;

    private String bairro;
    private Integer numero;
    private String cidade;
    private String cep;

    @ManyToOne
    @JoinColumn(name = "id_pessoa_cliente")
    @JsonBackReference // <-- ADICIONE ESTA ANOTAÇÃO
    private Cliente cliente;
}
