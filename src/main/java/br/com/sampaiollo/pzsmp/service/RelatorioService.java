package br.com.sampaiollo.pzsmp.service;

import br.com.sampaiollo.pzsmp.entity.RelatorioDiario;
import br.com.sampaiollo.pzsmp.repository.RelatorioDiarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioDiarioRepository relatorioDiarioRepository;

    /**
     * Busca todos os relatórios diários no banco de dados.
     * @return Uma lista de relatórios ordenada pela data mais recente primeiro.
     */
    public List<RelatorioDiario> listarRelatorios() {
        return relatorioDiarioRepository.findAll(Sort.by(Sort.Direction.DESC, "data"));
    }
}
