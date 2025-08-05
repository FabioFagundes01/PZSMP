package br.com.sampaiollo.pzsmp.controller;

import br.com.sampaiollo.pzsmp.entity.RelatorioDiario;
import br.com.sampaiollo.pzsmp.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
@CrossOrigin(origins = "*")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    /**
     * Endpoint para buscar a lista de todos os relatórios diários.
     * Acessível apenas por administradores.
     */
    @GetMapping
    public ResponseEntity<List<RelatorioDiario>> listarRelatorios() {
        List<RelatorioDiario> relatorios = relatorioService.listarRelatorios();
        return ResponseEntity.ok(relatorios);
    }
}
