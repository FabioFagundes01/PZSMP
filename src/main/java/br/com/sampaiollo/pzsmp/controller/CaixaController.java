package br.com.sampaiollo.pzsmp.controller;

import br.com.sampaiollo.pzsmp.dto.SangriaRequest;
import br.com.sampaiollo.pzsmp.entity.Sangria;
import br.com.sampaiollo.pzsmp.service.CaixaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.sampaiollo.pzsmp.dto.SangriaResponseDTO; // <<< IMPORTE O NOVO DTO
import java.util.List;

@RestController
@RequestMapping("/api/caixa")
@CrossOrigin(origins = "*")
public class CaixaController {

    @Autowired
    private CaixaService caixaService;

    @PostMapping("/sangria")
    public ResponseEntity<Sangria> realizarSangria(@RequestBody SangriaRequest request) {
        Sangria sangriaRegistrada = caixaService.realizarSangria(request);
        return ResponseEntity.ok(sangriaRegistrada);
    }
    @GetMapping("/sangrias")
    public ResponseEntity<List<SangriaResponseDTO>> listarSangrias() {
        List<SangriaResponseDTO> sangrias = caixaService.listarTodasSangrias();
        return ResponseEntity.ok(sangrias);
    }
}
