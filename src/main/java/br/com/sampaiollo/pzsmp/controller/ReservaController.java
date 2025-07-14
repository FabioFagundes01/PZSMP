package br.com.sampaiollo.pzsmp.controller;

import br.com.sampaiollo.pzsmp.dto.ReservaRequestDto;
import br.com.sampaiollo.pzsmp.entity.Reserva;
import br.com.sampaiollo.pzsmp.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    /**
     * Endpoint para criar uma nova reserva simplificada.
     * Recebe um nome para a reserva em vez de um ID de cliente.
     */
    @PostMapping
    public ResponseEntity<?> fazerReserva(@RequestBody ReservaRequestDto reservaDto) {
        try {
            Reserva novaReserva = reservaService.fazerReserva(reservaDto);
            return ResponseEntity.status(201).body(novaReserva);
        } catch (RuntimeException e) {
            // Retorna a mensagem de erro específica do serviço (ex: "Mesa já reservada")
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para cancelar uma reserva.
     * (Este método não precisa de alterações)
     */
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarReserva(@PathVariable Integer id) {
        try {
            Reserva reservaCancelada = reservaService.cancelarReserva(id);
            return ResponseEntity.ok(reservaCancelada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}