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
    private ClienteRepository clienteRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Transactional
    public Reserva fazerReserva(ReservaRequestDto dto) {
        Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + dto.getIdCliente()));
        
        Mesa mesa = mesaRepository.findById(dto.getIdMesa())
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada com número: " + dto.getIdMesa()));

        LocalDateTime horarioDesejado = dto.getDataReserva();
        LocalDateTime inicioIntervalo = horarioDesejado.minus(DURACAO_RESERVA).plusMinutes(1);
        LocalDateTime fimIntervalo = horarioDesejado.plus(DURACAO_RESERVA).minusMinutes(1);
        
        List<StatusReserva> statusAtivos = List.of(StatusReserva.PENDENTE, StatusReserva.CONFIRMADA);
        
        List<Reserva> conflitos = reservaRepository.findConflicts(mesa, inicioIntervalo, fimIntervalo, statusAtivos);

        if (!conflitos.isEmpty()) {
            throw new RuntimeException("A mesa " + mesa.getNumero() + " já está reservada ou bloqueada para este horário.");
        }

        mesa.setStatus(StatusMesa.RESERVADA);
        mesaRepository.save(mesa);

        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setMesa(mesa);
        reserva.setDataReserva(dto.getDataReserva());
        reserva.setNumPessoas(dto.getNumPessoas());
        reserva.setObservacoes(dto.getObservacoes());
        reserva.setStatus(StatusReserva.CONFIRMADA);

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