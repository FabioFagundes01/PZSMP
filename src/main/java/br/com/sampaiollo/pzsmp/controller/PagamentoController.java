package br.com.sampaiollo.pzsmp.controller;

import br.com.sampaiollo.pzsmp.dto.PagamentoRequestDto;
import br.com.sampaiollo.pzsmp.entity.Pagamento;
import br.com.sampaiollo.pzsmp.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagamentos")
@CrossOrigin(origins = "*")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<Pagamento> registrarPagamento(@Valid @RequestBody PagamentoRequestDto dto) {
        Pagamento novoPagamento = pagamentoService.registrarPagamento(dto);
        return ResponseEntity.status(201).body(novoPagamento);
    }
}