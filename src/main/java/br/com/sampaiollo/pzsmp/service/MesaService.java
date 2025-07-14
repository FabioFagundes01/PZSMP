package br.com.sampaiollo.pzsmp.service;

import br.com.sampaiollo.pzsmp.entity.Mesa;
import br.com.sampaiollo.pzsmp.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import br.com.sampaiollo.pzsmp.dto.MesaStatusResponse;
import java.util.List;
import java.util.Optional;

@Service
public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    public List<MesaStatusResponse> listarTodasComStatus() {
    return mesaRepository.findAll().stream()
            .map(mesa -> new MesaStatusResponse(
                    mesa.getNumero(),
                    mesa.getCapacidade(),
                    mesa.getStatus().toString()
            ))
            .collect(Collectors.toList());
}

    public Optional<Mesa> buscarPorNumero(Integer numero) {
        return mesaRepository.findById(numero);
    }
}