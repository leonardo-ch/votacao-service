package br.com.votacao.service;

import br.com.votacao.domain.VotacaoDto;
import br.com.votacao.exception.BusinessException;
import br.com.votacao.exception.VotacaoNotFoundException;
import br.com.votacao.exception.VotoNotFoundException;
import br.com.votacao.model.Pauta;
import br.com.votacao.model.Voto;
import br.com.votacao.repository.SessaoRepository;
import br.com.votacao.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VotacaoService {

	@Autowired
	private VotoRepository votoRepository;
	@Autowired
	private SessaoRepository sessaoRepository;
	
	public Voto save(final Voto voto) {
		verifyIfExists(voto);
		return votoRepository.save(voto);
	}

	private void verifyIfExists(final Voto voto) {
		Optional<Voto> votoByCpfAndPauta = votoRepository.findByCpf(voto.getCpf());

		if (votoByCpfAndPauta.isPresent() && (voto.isNew() || isNotUnique(voto, votoByCpfAndPauta))) {
			throw new BusinessException(null, null);
		}
	}

	private boolean isNotUnique(Voto voto, Optional<Voto> votoByCpfAndPauta) {
		return voto.alreadyExist() && votoByCpfAndPauta.isPresent() && !votoByCpfAndPauta.get().equals(voto);
	}

	public List<Voto> findAll() {
		return votoRepository.findAll();
	}

	public void delete(Voto voto) {
		Optional<Voto> votoById = votoRepository.findById(voto.getId());
		if (!votoById.isPresent()) {
			throw new VotoNotFoundException();
		}
		votoRepository.delete(voto);
	}

	public List<Voto> findVotosByPautaId(Long id) {
		Optional<List<Voto>> findByPautaId = votoRepository.findByPautaId(id);

		if (!findByPautaId.isPresent()) {
			throw new VotoNotFoundException();
		}

		return findByPautaId.get();
	}

	public VotacaoDto buildVotacaoPauta(Long id) {
		Optional<List<Voto>> votosByPauta = votoRepository.findByPautaId(id);
		if (!votosByPauta.isPresent() || votosByPauta.get().isEmpty()) {
			throw new VotacaoNotFoundException();
		}
		
		Pauta pauta = votosByPauta.get().iterator().next().getPauta();
		
		Long totalSessoes = sessaoRepository.countByPautaId(pauta.getId());
		
		
		Integer total = votosByPauta.get().size();

		Integer totalSim = (int) votosByPauta.get().stream().filter(voto -> Boolean.TRUE.equals(voto.getEscolha()))
				.count();

		Integer totalNao = total - totalSim;

		return VotacaoDto.builder().pauta(pauta).totalVotos(total).totalSessoes(totalSessoes.intValue())
				.totalSim(totalSim).totalNao(totalNao).build();

	}

}
