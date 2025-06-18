package br.com.sampaiollo.pzsmp.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "usuario") 
@Data
public class Usuario {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id_usuario;
	private char login;

	private char senha;

	private TipoUsuario tipo;

	public boolean autenticar(char senha) {
		return false;
	}

	public void alterarSenha(char senhaAnt, char senhaNova) {

	}

}
