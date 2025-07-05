package br.com.sampaiollo.pzsmp.controller;

import br.com.sampaiollo.pzsmp.entity.Mesa;
import br.com.sampaiollo.pzsmp.service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@CrossOrigin(origins = "*")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    // Endpoint para listar todas as mesas
    // HTTP GET -> http://localhost:8080/api/mesas
    @GetMapping
    public ResponseEntity<List<Mesa>> listarTodasMesas() {
        List<Mesa> mesas = mesaService.listarTodas();
        return ResponseEntity.ok(mesas);
    }

    // Endpoint para buscar uma mesa específica pelo seu número
    // HTTP GET -> http://localhost:8080/api/mesas/5
    @GetMapping("/{numero}")
    public ResponseEntity<Mesa> buscarMesaPorNumero(@PathVariable Integer numero) {
        return mesaService.buscarPorNumero(numero)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}