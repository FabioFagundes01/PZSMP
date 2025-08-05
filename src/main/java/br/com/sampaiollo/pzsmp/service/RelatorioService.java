package br.com.sampaiollo.pzsmp.service;

import br.com.sampaiollo.pzsmp.entity.RelatorioDiario;
import br.com.sampaiollo.pzsmp.repository.RelatorioDiarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate; // <<< Importe
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioDiarioRepository relatorioDiarioRepository;

    /**
     * Busca relatórios por um período específico.
     * Se nenhuma data for fornecida, retorna os relatórios dos últimos 7 dias por padrão.
     * @param dataInicio A data de início do período.
     * @param dataFim A data de fim do período.
     * @return Uma lista de relatórios.
     */
    public List<RelatorioDiario> buscarRelatoriosPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        // Verifica se as datas foram fornecidas na requisição
        if (dataInicio == null || dataFim == null) {
            // Se não, define o período padrão para os últimos 7 dias
            dataFim = LocalDate.now();
            dataInicio = dataFim.minusDays(7);
        }
        
        // Chama o novo método do repositório para buscar os dados no banco
        return relatorioDiarioRepository.findByDataBetween(dataInicio, dataFim);
    }
}