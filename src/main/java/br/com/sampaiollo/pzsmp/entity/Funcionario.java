package br.com.sampaiollo.pzsmp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Entity
@Table(name = "funcionario")
@Data
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "id_funcionario")
public class Funcionario extends Pessoa {

    @Column(length = 100)
    private String cargo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "login_usuario", referencedColumnName = "login")
    private Usuario usuario;
    
    @OneToMany(mappedBy = "funcionarioEntregador")
    private List<Entrega> entregas;
}