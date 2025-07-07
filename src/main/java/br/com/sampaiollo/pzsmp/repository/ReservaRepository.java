package br.com.sampaiollo.pzsmp.repository;

import br.com.sampaiollo.pzsmp.entity.Mesa;
import br.com.sampaiollo.pzsmp.entity.Reserva;
import br.com.sampaiollo.pzsmp.entity.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    // --- MÉTODO QUE ESTAVA FALTANDO ---
    /**
     * Busca por reservas ativas para uma mesa específica dentro de um intervalo de tempo.
     * Isso é usado para verificar se há conflitos de horário antes de criar uma nova reserva.
     * @param mesa A mesa para a qual se quer verificar conflitos.
     * @param inicioIntervalo O início da "janela de perigo" para conflitos.
     * @param fimIntervalo O fim da "janela de perigo" para conflitos.
     * @param statusAtivos Uma lista de status que são considerados como reservas ativas.
     * @return Uma lista de reservas conflitantes. Se a lista estiver vazia, não há conflitos.
     */
    @Query("SELECT r FROM Reserva r WHERE r.mesa = :mesa " +
           "AND r.dataReserva BETWEEN :inicioIntervalo AND :fimIntervalo " +
           "AND r.status IN :statusAtivos")
    List<Reserva> findConflicts(
            @Param("mesa") Mesa mesa,
            @Param("inicioIntervalo") LocalDateTime inicioIntervalo,
            @Param("fimIntervalo") LocalDateTime fimIntervalo,
            @Param("statusAtivos") List<StatusReserva> statusAtivos);
    
    // --- Outros métodos que você possa ter ---
    
    List<Reserva> findByDataReservaBetween(LocalDateTime inicio, LocalDateTime fim);
    
    List<Reserva> findByMesaNumero(Integer numeroMesa);
}