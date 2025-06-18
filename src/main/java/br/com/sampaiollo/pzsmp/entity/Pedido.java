package br.com.sampaiollo.pzsmp.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
@Entity
@Table(name = "pedido") 
@Data

public class Pedido {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_pedido;

	private double total;

	private StatusPedido Status;

	private LocalDateTime data;

	public void addItem(Produto produto, int quantidade) {

	}

	public double calculaTotal() {
		return 0;
	}

	public void atualizaStatus(StatusPedido novoStatus) {

	}

}
