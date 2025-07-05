package br.com.sampaiollo.pzsmp.repository;

import br.com.sampaiollo.pzsmp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Método essencial para o sistema de login e segurança.
    // Busca um usuário pelo seu nome de login, que é uma coluna única.
    Optional<Usuario> findByLogin(String login);
}