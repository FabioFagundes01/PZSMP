package br.com.sampaiollo.pzsmp.service;

import br.com.sampaiollo.pzsmp.dto.ProdutoRequest;
import br.com.sampaiollo.pzsmp.entity.Produto;
import br.com.sampaiollo.pzsmp.entity.TipoProduto;
import br.com.sampaiollo.pzsmp.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Integer id) {
        return produtoRepository.findById(id);
    }
    
@Transactional
public Produto cadastrarProduto(ProdutoRequest request, MultipartFile imagem) {
    Produto novoProduto = new Produto();
    novoProduto.setNome(request.nome());
    novoProduto.setPreco(request.preco());
    novoProduto.setTipo(TipoProduto.valueOf(request.tipo().toUpperCase()));

    if (imagem != null && !imagem.isEmpty()) {
        String nomeArquivo = salvarImagem(imagem);
        novoProduto.setImagemUrl(nomeArquivo);
    }

    return produtoRepository.save(novoProduto);
}

// Novo método privado para salvar o arquivo de imagem
private String salvarImagem(MultipartFile imagem) {
    try {
        // Define o diretório onde as imagens serão salvas
        // Crie esta pasta na raiz do seu projeto backend
        Path diretorioDeUpload = Paths.get("product-images");
        if (!Files.exists(diretorioDeUpload)) {
            Files.createDirectories(diretorioDeUpload);
        }

        // Gera um nome de arquivo único para evitar colisões
        String nomeArquivoOriginal = imagem.getOriginalFilename();
        String extensao = nomeArquivoOriginal.substring(nomeArquivoOriginal.lastIndexOf("."));
        String nomeArquivoUnico = UUID.randomUUID().toString() + extensao;

        // Salva o arquivo no diretório
        Path caminhoDoArquivo = diretorioDeUpload.resolve(nomeArquivoUnico);
        Files.copy(imagem.getInputStream(), caminhoDoArquivo);

        return nomeArquivoUnico;
    } catch (IOException e) {
        throw new RuntimeException("Não foi possível salvar a imagem do produto.", e);
    }
}
 @Transactional
public Produto atualizarProduto(Integer id, ProdutoRequest request, MultipartFile imagem) {
    Produto produtoExistente = produtoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));

    // Se uma nova imagem foi enviada...
    if (imagem != null && !imagem.isEmpty()) {
        // (Opcional, mas recomendado) Apaga a imagem antiga se ela existir
        if (produtoExistente.getImagemUrl() != null && !produtoExistente.getImagemUrl().isEmpty()) {
            try {
                Path caminhoImagemAntiga = Paths.get("product-images").resolve(produtoExistente.getImagemUrl());
                Files.deleteIfExists(caminhoImagemAntiga);
            } catch (IOException e) {
                // Log do erro, mas não impede a atualização
                System.err.println("Erro ao deletar imagem antiga: " + e.getMessage());
            }
        }
        
        // Salva a nova imagem e atualiza a URL
        String nomeNovoArquivo = salvarImagem(imagem); // Reutiliza o método que já temos
        produtoExistente.setImagemUrl(nomeNovoArquivo);
    }

    // Atualiza os outros campos
    produtoExistente.setNome(request.nome());
    produtoExistente.setPreco(request.preco());
    produtoExistente.setTipo(TipoProduto.valueOf(request.tipo().toUpperCase()));

    return produtoRepository.save(produtoExistente);
}

@Transactional
public void excluirProduto(Integer id) {
    // Verifica se o produto existe antes de tentar excluir
    if (!produtoRepository.existsById(id)) {
        throw new RuntimeException("Produto não encontrado com ID: " + id);
    }
    produtoRepository.deleteById(id);
}
}