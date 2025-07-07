package br.com.sampaiollo.pzsmp.service;

import br.com.sampaiollo.pzsmp.dto.PagamentoRequestDto;
import br.com.sampaiollo.pzsmp.entity.*;
import br.com.sampaiollo.pzsmp.repository.MesaRepository;
import br.com.sampaiollo.pzsmp.repository.PagamentoRepository;
import br.com.sampaiollo.pzsmp.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private MesaRepository mesaRepository;

    @Transactional
    public Pagamento registrarPagamento(PagamentoRequestDto dto) {
        // 1. Encontra o pedido a ser pago
        Pedido pedido = pedidoRepository.findById(dto.idPedido())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + dto.idPedido()));

        // 2. Cria e salva o registro do pagamento
        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setMetodo(dto.metodo());
        pagamento.setValorPago(dto.valorPago());
        pagamento.setDatapag(LocalDateTime.now());
        pagamento.setStatus(StatusPagamento.EFETUADO);
        pagamentoRepository.save(pagamento);

        // 3. Marca o pedido como pago
        pedido.setPago(true);
        pedidoRepository.save(pedido);

        // 4. VERIFICA SE A MESA PODE SER LIBERADA
        Mesa mesaDoPedido = pedido.getMesa();
        if (mesaDoPedido != null) {
            // Conta quantos outros pedidos para esta mesa ainda não foram pagos (e não estão cancelados)
            Integer pedidosNaoPagos = pedidoRepository.countByMesaAndPagoIsFalseAndStatusNot(mesaDoPedido, StatusPedido.CANCELADO);

            // Se não há mais pedidos pendentes de pagamento, libera a mesa
            if (pedidosNaoPagos == 0) {
                mesaDoPedido.setStatus(StatusMesa.LIVRE);
                mesaRepository.save(mesaDoPedido);
            }
        }

        return pagamento;
    }
}