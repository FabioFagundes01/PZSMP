package br.com.sampaiollo.pzsmp.repository;

import br.com.sampaiollo.pzsmp.entity.RelatorioDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatorioDiarioRepository extends JpaRepository<RelatorioDiario, Long> {
}
