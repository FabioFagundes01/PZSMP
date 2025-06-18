package br.com.sampaiollo.pzsmp.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "itempedido") 
@Data
public class ItemPedido {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_item;

	private int quantidade;

	private int preco;

}
