package br.com.sampaiollo.pzsmp.controller;

import br.com.sampaiollo.pzsmp.dto.FuncionarioRequestDto;
import br.com.sampaiollo.pzsmp.dto.FuncionarioResponseDto; // Importar o novo DTO
import br.com.sampaiollo.pzsmp.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<FuncionarioResponseDto> cadastrarFuncionario(@RequestBody FuncionarioRequestDto funcionarioDto) {
        FuncionarioResponseDto novoFuncionario = funcionarioService.cadastrarFuncionario(funcionarioDto);
        return ResponseEntity.status(201).body(novoFuncionario);
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDto>> listarTodos() {
        List<FuncionarioResponseDto> funcionarios = funcionarioService.listarTodos();
        return ResponseEntity.ok(funcionarios);
    }
}