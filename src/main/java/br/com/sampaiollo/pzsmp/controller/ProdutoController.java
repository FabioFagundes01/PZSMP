package br.com.sampaiollo.pzsmp.controller;

import org.springframework.http.HttpStatus;
import br.com.sampaiollo.pzsmp.dto.ProdutoRequest;
import br.com.sampaiollo.pzsmp.entity.Produto;
import br.com.sampaiollo.pzsmp.service.ProdutoService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // Endpoint para listar todos os produtos (o cardápio)
    // HTTP GET -> http://localhost:8080/api/produtos
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodosProdutos() {
        List<Produto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }
    
    // Endpoint para buscar um produto específico por ID
    // HTTP GET -> http://localhost:8080/api/produtos/1
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Integer id) {
        return produtoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
public ResponseEntity<Produto> cadastrarProduto(
        @RequestParam("nome") String nome,
        @RequestParam("preco") BigDecimal preco,
        @RequestParam("tipo") String tipo,
        @RequestParam(value = "imagem", required = false) MultipartFile imagem) {

    // Cria o DTO a partir dos parâmetros recebidos
    ProdutoRequest request = new ProdutoRequest(nome, tipo, preco);

    Produto produtoSalvo = produtoService.cadastrarProduto(request, imagem);
    return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
}
}