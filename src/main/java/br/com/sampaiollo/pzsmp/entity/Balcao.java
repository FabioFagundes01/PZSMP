package br.com.sampaiollo.pzsmp.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "balcao") 
@Data
public class Balcao {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_balcao;

	private char status;

}
