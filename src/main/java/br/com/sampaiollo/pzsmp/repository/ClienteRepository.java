package br.com.sampaiollo.pzsmp.repository;

import br.com.sampaiollo.pzsmp.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    /**
     * Busca um cliente pelo seu endereço de e-mail.
     * O Spring Data JPA cria a consulta automaticamente a partir do nome do método.
     * @param email O e-mail a ser pesquisado.
     * @return um Optional contendo o Cliente, se encontrado.
     */
    Optional<Cliente> findByEmail(String email);

    /**
     * Busca clientes cujo nome OU telefone contenha o termo de busca, ignorando
     * a diferença entre maiúsculas e minúsculas.
     * @param nome O termo de busca a ser aplicado ao nome.
     * @param telefone O termo de busca a ser aplicado ao telefone.
     * @return uma Lista de Clientes que correspondem aos critérios.
     */
    List<Cliente> findByNomeContainingIgnoreCaseOrTelefoneContainingIgnoreCase(String nome, String telefone);
    
}
