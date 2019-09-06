package br.com.votacao.service;

import br.com.votacao.domain.CpfValidationDto;
import br.com.votacao.domain.VotacaoDto;
import br.com.votacao.exception.InvalidCpfException;
import br.com.votacao.exception.InvalidSessionException;
import br.com.votacao.exception.SessaoNotFoundException;
import br.com.votacao.exception.SessaoTimeOutException;
import br.com.votacao.exception.UnableCpfException;
import br.com.votacao.exception.VotoAlreadyExistsException;
import br.com.votacao.exception.VotoNotFoundException;
import br.com.votacao.kafka.KafkaSender;
import br.com.votacao.model.Pauta;
import br.com.votacao.model.Sessao;
import br.com.votacao.model.Voto;
import br.com.votacao.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class VotoService {

	private static final String CPF_UNABLE_TO_VOTE = "UNABLE_TO_VOTE";

	@Value("${app.integracao.cpf.url}")
	private String urlCpfValidator = "";

	private final VotoRepository votoRepository;
	private final RestTemplate restTemplate;
	private final KafkaSender kafkaSender;
	private final SessaoService sessaoService;
	private final VotacaoService votacaoService;

	@Autowired
	public VotoService(RestTemplate restTemplate, VotoRepository votoRepository, KafkaSender kafkaSender, SessaoService sessaoService, VotacaoService votacaoService) {
		this.restTemplate = restTemplate;
		this.votoRepository = votoRepository;
		this.kafkaSender = kafkaSender;
		this.sessaoService = sessaoService;
		this.votacaoService = votacaoService;
	}

	public Voto findById(Long id) {
		Optional<Voto> findById = votoRepository.findById(id);
		if(!findById.isPresent()){
			throw new VotoNotFoundException();
		}
		return findById.get();
	}

	public Voto createVoto(Long idPauta, Long idSessao, Voto voto) {
		Sessao sessao = sessaoService.findByIdAndPautaId(idSessao, idPauta);
		if (!idPauta.equals(sessao.getPauta().getId())) {
			throw new InvalidSessionException();
		}
		voto.setPauta(sessao.getPauta());
		return verifyAndSave(sessao, voto);
	}

	private Voto verifyAndSave(final Sessao sessao, final Voto voto) {
		verifyVoto(sessao, voto);
		return votoRepository.save(voto);
	}

	protected void verifyVoto(final Sessao sessao, final Voto voto) {

		LocalDateTime dataLimite = sessao.getDataInicio().plusMinutes(sessao.getMinutosValidade());
		if (LocalDateTime.now().isAfter(dataLimite)) {
			sendMessage(voto.getPauta());
			throw new SessaoTimeOutException();
		}

		cpfAbleToVote(voto);
		votoAlreadyExists(voto);
	}

	protected void votoAlreadyExists(final Voto voto) {
		Optional<Voto> votoByCpfAndPauta = votoRepository.findByCpfAndPautaId(voto.getCpf(), voto.getPauta().getId());

		if (votoByCpfAndPauta.isPresent()) {
			throw new VotoAlreadyExistsException();
		}
	}

	private void sendMessage(Pauta pauta) {
		VotacaoDto votacaoPauta = votacaoService.buildVotacaoPauta(pauta.getId());
		kafkaSender.sendMessage(votacaoPauta);
	}

	protected void cpfAbleToVote(final Voto voto) {
		ResponseEntity<CpfValidationDto> cpfValidation = getCpfValidation(voto);
		if (HttpStatus.OK.equals(cpfValidation.getStatusCode())) {
			if (CPF_UNABLE_TO_VOTE.equalsIgnoreCase(cpfValidation.getBody().getStatus())) {
				throw new UnableCpfException();
			}
		} else {
			throw new InvalidCpfException();
		}
	}

	private ResponseEntity<CpfValidationDto> getCpfValidation(final Voto voto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(urlCpfValidator.concat("/").concat(voto.getCpf()), HttpMethod.GET, entity,
				CpfValidationDto.class);
	}

	public List<Voto> findAll() {
		return votoRepository.findAll();
	}

	public void delete(Long id) {
		Optional<Voto> votoById = votoRepository.findById(id);
		if (!votoById.isPresent()) {
			throw new VotoNotFoundException();
		}
		votoRepository.delete(votoById.get());
	}

	void deleteByPautaId(Long id) {
		Optional<List<Voto>> votos = votoRepository.findByPautaId(id);
		votos.ifPresent(voto -> voto.forEach(votoRepository::delete));
	}

	public List<Voto> findVotosByPautaId(Long id) {
		Optional<List<Voto>> findByPautaId = votoRepository.findByPautaId(id);

		if (!findByPautaId.isPresent()) {
			throw new VotoNotFoundException();
		}

		return findByPautaId.get();
	}

}
