package br.com.sampaiollo.pzsmp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    @JsonManagedReference
    private List<Pedido> pedidos;

    // Um Cliente pode ter muitos Enderecos
    @OneToMany(mappedBy = "cliente")
    @JsonManagedReference
    private List<Endereco> enderecos;

    // Um Cliente pode ter muitas Reservas
    @OneToMany(mappedBy = "cliente")
    @JsonManagedReference
    private List<Reserva> reservas;
}
