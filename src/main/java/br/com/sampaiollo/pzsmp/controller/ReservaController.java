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

    // Endpoint para criar uma nova reserva
    // HTTP POST -> http://localhost:8080/api/reservas
    @PostMapping
    public ResponseEntity<?> fazerReserva(@RequestBody ReservaRequestDto reservaDto) {
        try {
            Reserva novaReserva = reservaService.fazerReserva(reservaDto);
            return ResponseEntity.status(201).body(novaReserva);
        } catch (RuntimeException e) {
            // Retorna o erro específico do serviço, como "Cliente não encontrado"
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para cancelar uma reserva
    // HTTP PUT -> http://localhost:8080/api/reservas/1/cancelar
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