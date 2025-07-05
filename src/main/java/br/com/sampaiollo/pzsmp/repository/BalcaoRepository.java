package br.com.sampaiollo.pzsmp.repository;

import br.com.sampaiollo.pzsmp.entity.Balcao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalcaoRepository extends JpaRepository<Balcao, Integer> {
    // Para a entidade Balcão, os métodos padrão do JpaRepository geralmente são suficientes.
}