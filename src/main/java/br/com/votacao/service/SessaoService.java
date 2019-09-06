package br.com.votacao.service;

import br.com.votacao.exception.PautaNotFoundException;
import br.com.votacao.exception.SessaoNotFoundException;
import br.com.votacao.model.Pauta;
import br.com.votacao.model.Sessao;
import br.com.votacao.repository.PautaRepository;
import br.com.votacao.repository.SessaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessaoService {

    private final SessaoRepository sessaoRepository;
    private final PautaRepository pautaRepository;

    public SessaoService(SessaoRepository sessaoRepository, PautaRepository pautaRepository) {
        this.sessaoRepository = sessaoRepository;
        this.pautaRepository = pautaRepository;
    }

    public List<Sessao> findAll() {
        return sessaoRepository.findAll();
    }

    public Sessao createSession(Long id, Sessao sessao){
        Optional<Pauta> findById = pautaRepository.findById(id);
        if(!findById.isPresent()){
            throw new PautaNotFoundException();
        }
        sessao.setPauta(findById.get());
        return save(sessao);
    }

    private Sessao save(final Sessao sessao) {
        if (sessao.getDataInicio() == null) {
            sessao.setDataInicio(LocalDateTime.now());
        }
        if (sessao.getMinutosValidade() == null) {
            sessao.setMinutosValidade(1L);
        }

        return sessaoRepository.save(sessao);

    }

    public void delete(Long id) {
        Optional<Sessao> sessaoById = sessaoRepository.findById(id);
        if (!sessaoById.isPresent()) {
            throw new SessaoNotFoundException();
        }
        sessaoRepository.delete(sessaoById.get());
    }

    void deleteByPautaId(Long id) {
        Optional<List<Sessao>> sessaos = sessaoRepository.findByPautaId(id);
        sessaos.ifPresent(sessaoList -> sessaoList.forEach(sessaoRepository::delete));
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
