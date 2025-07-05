package br.com.sampaiollo.pzsmp.repository;

import br.com.sampaiollo.pzsmp.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {
    
    // Geralmente, os itens de pedido são acessados através do próprio Pedido.
    // Mas, se precisar, um método para buscar todos os itens de um pedido pode ser útil.
    List<ItemPedido> findByPedidoId(Integer pedidoId);
}