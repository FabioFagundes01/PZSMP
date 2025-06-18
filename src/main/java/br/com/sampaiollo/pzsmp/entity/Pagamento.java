package br.com.sampaiollo.pzsmp.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
@Entity
@Table(name = "pagamento") 
@Data

public class Pagamento {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_pagamento;

	private LocalDateTime datapag;

	private MetodoPagamento metodo;

	private double valor_pago;

}
