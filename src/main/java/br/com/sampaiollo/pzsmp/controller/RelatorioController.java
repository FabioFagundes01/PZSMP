package br.com.sampaiollo.pzsmp.controller;

import br.com.sampaiollo.pzsmp.entity.RelatorioDiario;
import br.com.sampaiollo.pzsmp.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat; // <<< Importe
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // <<< Importe @RequestParam

import java.time.LocalDate; // <<< Importe
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
@CrossOrigin(origins = "*")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    /**
     * Endpoint modificado para buscar relatórios por período.
     * Aceita os parâmetros 'dataInicio' e 'dataFim' na URL.
     * Ex: /api/relatorios?dataInicio=2025-08-01&dataFim=2025-08-05
     */
    @GetMapping
    public ResponseEntity<List<RelatorioDiario>> listarRelatorios(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim
    ) {
        List<RelatorioDiario> relatorios = relatorioService.buscarRelatoriosPorPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(relatorios);
    }
}