package br.com.votacao.service;

import br.com.votacao.exception.PautaNotFoundException;
import br.com.votacao.model.Pauta;
import br.com.votacao.repository.PautaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PautaService {

    private final PautaRepository pautaRepository;

    public PautaService(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public List<Pauta> findAll() {
        return pautaRepository.findAll();
    }

    public Pauta save(final Pauta pauta) {
        return pautaRepository.save(pauta);
    }

    public void delete(Pauta pauta) {
        Optional<Pauta> pautaById = pautaRepository.findById(pauta.getId());
        if (!pautaById.isPresent()) {
            throw new PautaNotFoundException();
        }
        pautaRepository.delete(pauta);
    }

    public Pauta findById(Long id) {
        Optional<Pauta> findById = pautaRepository.findById(id);
        if(!findById.isPresent()){
            throw new PautaNotFoundException();
        }
        return findById.get();
    }
}
