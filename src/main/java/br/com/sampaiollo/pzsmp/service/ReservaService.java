package br.com.sampaiollo.pzsmp.service;

import br.com.sampaiollo.pzsmp.dto.ReservaRequestDto;
import br.com.sampaiollo.pzsmp.entity.*;
import br.com.sampaiollo.pzsmp.repository.ClienteRepository;
import br.com.sampaiollo.pzsmp.repository.MesaRepository;
import br.com.sampaiollo.pzsmp.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {

    private static final Duration DURACAO_RESERVA = Duration.ofHours(2);

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClienteRepository clienteRepository; // Mantido para futuras funcionalidades

    @Autowired
    private MesaRepository mesaRepository;

    @Transactional
    public Reserva fazerReserva(ReservaRequestDto dto) {
        // 1. Busca a mesa ou lança um erro
        Mesa mesa = mesaRepository.findById(dto.idMesa())
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada com número: " + dto.idMesa()));

        // 2. Verifica se a mesa já não está ocupada ou reservada
        if (mesa.getStatus() != StatusMesa.LIVRE) {
            throw new RuntimeException("A mesa " + mesa.getNumero() + " não está livre para reserva.");
        }

        // 3. (Lógica de verificação de conflito de horário continua a mesma)
        LocalDateTime horarioDesejado = dto.dataReserva();
        LocalDateTime inicioIntervalo = horarioDesejado.minus(DURACAO_RESERVA).plusMinutes(1);
        LocalDateTime fimIntervalo = horarioDesejado.plus(DURACAO_RESERVA).minusMinutes(1);
        List<StatusReserva> statusAtivos = List.of(StatusReserva.PENDENTE, StatusReserva.CONFIRMADA);
        List<Reserva> conflitos = reservaRepository.findConflicts(mesa, inicioIntervalo, fimIntervalo, statusAtivos);

        if (!conflitos.isEmpty()) {
            throw new RuntimeException("A mesa " + mesa.getNumero() + " já possui uma reserva conflitante para este horário.");
        }

        // 4. Atualiza o status da mesa para RESERVADA
        mesa.setStatus(StatusMesa.RESERVADA);
        mesaRepository.save(mesa);

        // 5. Cria a nova entidade Reserva com os dados simplificados
        Reserva reserva = new Reserva();
        reserva.setMesa(mesa);
        reserva.setNomeReserva(dto.nomeReserva()); // Usa o nome temporário
        reserva.setNumPessoas(dto.numPessoas());
        reserva.setDataReserva(dto.dataReserva());
        reserva.setObservacoes(dto.observacoes());
        reserva.setStatus(StatusReserva.CONFIRMADA);
        // O campo 'cliente' não é definido, permanecendo nulo

        return reservaRepository.save(reserva);
    }
    
    @Transactional
    public Reserva cancelarReserva(Integer reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada com ID: " + reservaId));
        
        reserva.setStatus(StatusReserva.CANCELADA_CLIENTE);
        
        // Libera a mesa se ela estava reservada por esta reserva
        Mesa mesa = reserva.getMesa();
        if (mesa.getStatus() == StatusMesa.RESERVADA) {
            mesa.setStatus(StatusMesa.LIVRE);
            mesaRepository.save(mesa);
        }

        return reservaRepository.save(reserva);
    }
}