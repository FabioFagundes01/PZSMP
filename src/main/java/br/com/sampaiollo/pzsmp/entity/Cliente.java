package br.com.sampaiollo.pzsmp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Entity
@Table(name = "cliente")
@Data
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "id_pessoa") // Vincula à PK na tabela Pessoa
public class Cliente extends Pessoa {

    @Column(unique = true)
    private String email;

    // Um Cliente pode ter muitos Pedidos
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;

    // Um Cliente pode ter muitos Enderecos
    @OneToMany(mappedBy = "cliente")
    private List<Endereco> enderecos;

    // Um Cliente pode ter muitas Reservas
    @OneToMany(mappedBy = "cliente")
    private List<Reserva> reservas;
}
