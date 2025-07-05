package br.com.sampaiollo.pzsmp.entity;

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

    // Muitos Enderecos podem pertencer a um Cliente
    @ManyToOne
    @JoinColumn(name = "id_pessoa_cliente") // Conforme definido no seu script SQL [cite: 216]
    private Cliente cliente;
}
