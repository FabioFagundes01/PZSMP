package br.com.sampaiollo.pzsmp.repository;

import br.com.sampaiollo.pzsmp.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    // O Spring entende o nome deste método e cria a consulta automaticamente!
    // Isso buscará um cliente pelo seu campo 'email'.
    Optional<Cliente> findByEmail(String email);
}