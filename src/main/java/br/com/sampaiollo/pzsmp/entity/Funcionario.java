package br.com.sampaiollo.pzsmp.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "funcionario") 
@Data
public class Funcionario {

	private char cargo;

	private double salario;
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_pessoa;

	private Pessoa pessoa;

	public Pagamento registrarPag(Pedido pedido, double valor, MetodoPagamento metodoPag) {
		return null;
	}

	public void gerenciarStatusPed(Pedido pedido, StatusPedido novoStatus) {

	}

	public void gerenciarStatusEnt(Entrega entrega, StatusEntrega novoStatus) {

	}

}
