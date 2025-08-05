package br.com.sampaiollo.pzsmp.repository;

import br.com.sampaiollo.pzsmp.entity.RelatorioDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate; // <<< Importe
import java.util.List;    // <<< Importe

@Repository
public interface RelatorioDiarioRepository extends JpaRepository<RelatorioDiario, Long> {

    /**
     * Novo método que busca relatórios cuja data está entre a data de início e a data de fim.
     * O Spring Data JPA cria a consulta SQL automaticamente a partir do nome do método.
     */
    List<RelatorioDiario> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);
}