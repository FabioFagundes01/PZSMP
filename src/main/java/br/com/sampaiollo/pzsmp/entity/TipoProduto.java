package br.com.sampaiollo.pzsmp.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "produto") 
public enum TipoProduto {

	PIZZA_ESPECIAL,

	PIZZA_TRADICIONAL,

	PIZZA_DOCE,

	PASTEL_DOCE,

	LANCHES,

	PASTEL,

	SUCOS,

	DRINKS,

	SOBREMESA;

}
