package br.com.sampaiollo.pzsmp.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
@Entity
@Table(name = "mesa") 
@Data

public class Mesa {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int numero;

	private int capacidade;

	public boolean verificarDisp(LocalDateTime dataHora) {
		return false;
	}

}
