package br.com.sampaiollo.pzsmp.repository;

import br.com.sampaiollo.pzsmp.entity.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {

    // Encontra mesas com capacidade maior ou igual a um número de pessoas.
    List<Mesa> findByCapacidadeGreaterThanEqual(Integer capacidade);
}