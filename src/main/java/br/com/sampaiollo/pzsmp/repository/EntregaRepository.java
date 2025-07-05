package br.com.sampaiollo.pzsmp.repository;

import br.com.sampaiollo.pzsmp.entity.Entrega;
import br.com.sampaiollo.pzsmp.entity.StatusEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Integer> {

    // Encontra entregas por status (ex: todas que estão 'EM_ROTA')
    List<Entrega> findByStatus(StatusEntrega status);

    // Encontra todas as entregas de um funcionário específico
    List<Entrega> findByFuncionarioEntregadorId(Integer funcionarioId);
}