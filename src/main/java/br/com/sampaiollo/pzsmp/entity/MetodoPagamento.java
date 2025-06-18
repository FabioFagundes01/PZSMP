package br.com.sampaiollo.pzsmp.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "metodopagamento") 
public enum MetodoPagamento {

	PIX,

	CARTAO_CREDITO,

	CARTAO_DEBITO,

	DINHEIRO;

}
