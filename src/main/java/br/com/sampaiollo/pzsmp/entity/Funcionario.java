package br.com.sampaiollo.pzsmp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "funcionario")
@Data
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "id_funcionario") // Conecta com a tabela Pessoa
public class Funcionario extends Pessoa {

    @Column(length = 100)
    private String cargo;

    @Column(precision = 10, scale = 2) // Equivalente a NUMERIC(10,2)
    private BigDecimal salario;

    // Um Funcionário tem um Usuário. A ligação é feita pela coluna 'login' que é única.
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "login_usuario", referencedColumnName = "login") // FK de Funcionario para a coluna 'login' de Usuario [cite: 192]
    private Usuario usuario;
    
    // Um funcionário pode ser responsável por várias entregas
    @OneToMany(mappedBy = "funcionarioEntregador")
    private List<Entrega> entregas;
}
