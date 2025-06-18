package br.com.sampaiollo.pzsmp.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "produto") 
public enum TipoUsuario {

	ADMIN,

	FUNCIONARIO;

}
