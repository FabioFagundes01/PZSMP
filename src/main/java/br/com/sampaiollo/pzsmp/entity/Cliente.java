package br.com.sampaiollo.pzsmp.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "cliente") 
@Data

public class Cliente {

	private char email;
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_pessoa;

	private Pessoa pessoa;

	public Pedido fazerPedido() {
		return null;
	}

	public Reserva fazerReserva() {
		return null;
	}

	public List<Pedido> consultarHistorico() {
		return null;
	}

	public void AddEndereco(Endereco endereco) {

	}

}
