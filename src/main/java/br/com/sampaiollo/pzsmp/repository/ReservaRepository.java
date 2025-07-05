package br.com.sampaiollo.pzsmp.repository;

import br.com.sampaiollo.pzsmp.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    // Encontra todas as reservas para uma data específica (ou intervalo)
    List<Reserva> findByDataReservaBetween(LocalDateTime inicio, LocalDateTime fim);
    
    // Encontra todas as reservas de uma mesa específica
    List<Reserva> findByMesaNumero(Integer numeroMesa);
}