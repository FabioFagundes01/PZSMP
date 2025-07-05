package br.com.sampaiollo.pzsmp.controller;

import br.com.sampaiollo.pzsmp.dto.FuncionarioRequestDto;
import br.com.sampaiollo.pzsmp.entity.Funcionario;
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

    // Endpoint para cadastrar um novo funcionário
    // HTTP POST -> http://localhost:8080/api/funcionarios
    @PostMapping
    public ResponseEntity<Funcionario> cadastrarFuncionario(@RequestBody FuncionarioRequestDto funcionarioDto) {
        Funcionario novoFuncionario = funcionarioService.cadastrarFuncionario(funcionarioDto);
        return ResponseEntity.status(201).body(novoFuncionario);
    }

    // Endpoint para listar todos os funcionários
    // HTTP GET -> http://localhost:8080/api/funcionarios
    @GetMapping
    public ResponseEntity<List<Funcionario>> listarTodos() {
        List<Funcionario> funcionarios = funcionarioService.listarTodos();
        return ResponseEntity.ok(funcionarios);
    }
}