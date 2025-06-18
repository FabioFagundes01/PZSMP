package br.com.sampaiollo.pzsmp.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
@Entity
@Table(name = "reserva") 
@Data

public class Reserva {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_reserva;

	private LocalDateTime dataReserva;

	private int num_pessoas;

	private StatusReserva status;

	private char observacoes;

	public void confirmarRes() {

	}

	public void cancelarRes() {

	}

}
