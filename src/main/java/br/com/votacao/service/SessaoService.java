package br.com.votacao.service;

import br.com.votacao.exception.SessaoNotFoundException;
import br.com.votacao.model.Pauta;
import br.com.votacao.model.Sessao;
import br.com.votacao.repository.SessaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessaoService {

    private final SessaoRepository sessaoRepository;

    public SessaoService(SessaoRepository sessaoRepository) {
        this.sessaoRepository = sessaoRepository;
    }

    public List<Sessao> findAll() {
        return sessaoRepository.findAll();
    }

    public Sessao createSession(Long id, Sessao sessao){
        return sessaoRepository.save(sessao.pauta(Pauta.builder().id(id).build()));
    }

    public Sessao save(final Sessao sessao) {
        if (sessao.getDataInicio() == null) {
            sessao.setDataInicio(LocalDateTime.now());
        }
        if (sessao.getMinutosValidade() == null) {
            sessao.setMinutosValidade(1L);
        }

        return sessaoRepository.save(sessao);

    }

    public void delete(Sessao sessao) {
        Optional<Sessao> sessaoById = sessaoRepository.findById(sessao.getId());
        if (!sessaoById.isPresent()) {
            throw new SessaoNotFoundException();
        }
        sessaoRepository.delete(sessao);
    }

    public Sessao findById(Long id) {
        Optional<Sessao> findById = sessaoRepository.findById(id);
        if(!findById.isPresent()){
            throw new SessaoNotFoundException();
        }
        return findById.get();
    }

    public Sessao findByIdAndPautaId(Long idSessao, Long pautaId) {
        Optional<Sessao> findByIdAndPautaId = sessaoRepository.findByIdAndPautaId(idSessao, pautaId);
        if(!findByIdAndPautaId.isPresent()){
            throw new SessaoNotFoundException();
        }
        return findByIdAndPautaId.get();
    }
}
