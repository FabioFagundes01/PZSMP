package br.com.sampaiollo.pzsmp.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "entrega") 
@Data
public class Entrega {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_entrega;

	private StatusEntrega status;

	public void iniciarRota() {

	}

	public void finalizaEnt() {

	}

}
