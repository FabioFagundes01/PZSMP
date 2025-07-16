package br.com.sampaiollo.pzsmp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference; // <-- Importe esta classe
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Entity
@Table(name = "cliente")
@Data
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "id_pessoa")
public class Cliente extends Pessoa {

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("cliente-pedidos") // <-- ADICIONE AQUI
    private List<Pedido> pedidos;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("cliente-enderecos") // <-- ADICIONE AQUI
    private List<Endereco> enderecos;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("cliente-reservas") // <-- ADICIONE AQUI
    private List<Reserva> reservas;
}
