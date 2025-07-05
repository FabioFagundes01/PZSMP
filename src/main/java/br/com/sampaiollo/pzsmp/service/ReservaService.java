package br.com.sampaiollo.pzsmp.service;

import br.com.sampaiollo.pzsmp.dto.ReservaRequestDto;
import br.com.sampaiollo.pzsmp.entity.*;
import br.com.sampaiollo.pzsmp.repository.ClienteRepository;
import br.com.sampaiollo.pzsmp.repository.MesaRepository;
import br.com.sampaiollo.pzsmp.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaService {

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

        // TODO: Adicionar lógica para verificar se a mesa já está reservada para o horário desejado.
        // Esta validação é crucial para o sistema funcionar corretamente.

        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setMesa(mesa);
        reserva.setDataReserva(dto.getDataReserva());
        reserva.setNumPessoas(dto.getNumPessoas());
        reserva.setObservacoes(dto.getObservacoes());
        reserva.setStatus(StatusReserva.CONFIRMADA); // ou PENDENTE, dependendo da regra

        return reservaRepository.save(reserva);
    }
    
    @Transactional
    public Reserva cancelarReserva(Integer reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
            .orElseThrow(() -> new RuntimeException("Reserva não encontrada com ID: " + reservaId));
        
        reserva.setStatus(StatusReserva.CANCELADA_CLIENTE); // Ou outro status de cancelado
        return reservaRepository.save(reserva);
    }
}