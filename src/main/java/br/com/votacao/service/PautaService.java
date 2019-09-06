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
    private final SessaoService sessaoService;
    private final VotoService votoService;

    public PautaService(PautaRepository pautaRepository, SessaoService sessaoService, VotoService votoService) {
        this.pautaRepository = pautaRepository;
        this.sessaoService = sessaoService;
        this.votoService = votoService;
    }

    public List<Pauta> findAll() {
        return pautaRepository.findAll();
    }

    public Pauta save(final Pauta pauta) {
        return pautaRepository.save(pauta);
    }

    public void delete(Long id) {
        Optional<Pauta> pautaById = pautaRepository.findById(id);
        if (!pautaById.isPresent()) {
            throw new PautaNotFoundException();
        }
        pautaRepository.delete(pautaById.get());
        sessaoService.deleteByPautaId(id);
        votoService.deleteByPautaId(id);
    }

    public Pauta findById(Long id) {
        Optional<Pauta> findById = pautaRepository.findById(id);
        if(!findById.isPresent()){
            throw new PautaNotFoundException();
        }
        return findById.get();
    }
}
