package br.com.sampaiollo.pzsmp.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "pessoa") 
@Data
public class Pessoa {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_pessoa;

	private char nome;

	private char telefone;

}
