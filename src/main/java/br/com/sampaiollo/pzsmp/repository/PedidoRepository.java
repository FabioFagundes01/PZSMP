package br.com.sampaiollo.pzsmp.repository;

import br.com.sampaiollo.pzsmp.entity.Mesa;
import br.com.sampaiollo.pzsmp.entity.Pedido;
import br.com.sampaiollo.pzsmp.entity.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByClienteId(Integer idCliente);

    List<Pedido> findByStatus(StatusPedido status);

    List<Pedido> findByDataBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
    
    // MÉTODO NOVO/ATUALIZADO
    Integer countByMesaAndPagoIsFalseAndStatusNot(Mesa mesa, StatusPedido status);
}