package br.com.sampaiollo.pzsmp.repository;

import br.com.sampaiollo.pzsmp.entity.Pedido;
import br.com.sampaiollo.pzsmp.entity.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    // Encontra todos os pedidos de um cliente específico pelo ID do cliente
    List<Pedido> findByClienteId(Integer idCliente);

    // Encontra todos os pedidos com um determinado status
    List<Pedido> findByStatus(StatusPedido status);

    // Encontra todos os pedidos criados entre duas datas
    List<Pedido> findByDataBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
}