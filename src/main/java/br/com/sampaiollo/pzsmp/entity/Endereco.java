package br.com.sampaiollo.pzsmp.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "endereco") 
@Data
public class Endereco {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_endereco;

	private char rua;

	private char bairro;

	private int numero;

	private char cidade;

	private char cep;

}
