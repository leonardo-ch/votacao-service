package br.com.votacao.service;

import br.com.votacao.domain.CpfValidationDto;
import br.com.votacao.domain.VotacaoDto;
import br.com.votacao.exception.InvalidCpfException;
import br.com.votacao.exception.SessaoTimeOutException;
import br.com.votacao.exception.UnableCpfException;
import br.com.votacao.exception.VotoAlreadyExistsException;
import br.com.votacao.kafka.KafkaSender;
import br.com.votacao.model.Pauta;
import br.com.votacao.model.Sessao;
import br.com.votacao.model.Voto;
import br.com.votacao.repository.VotoRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class VotoServiceTest {

	private VotoService votoService;
	@Mock
	private VotoRepository votoRepository;
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private KafkaSender kafkaSender;
	@Mock
	private VotacaoService votacaoService;
	@Mock
	private SessaoService sessaoService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		votoService = new VotoService(restTemplate, votoRepository, kafkaSender, sessaoService, votacaoService);
	}

	@Test(expected = SessaoTimeOutException.class)
	public void verifyVotoTest() {
		Sessao sessao = new Sessao();
		sessao.setDataInicio(LocalDateTime.now());
		sessao.setMinutosValidade(-1L);

		Voto voto = new Voto();
		Pauta pauta = new Pauta();
		pauta.setId(1L);
		voto.setPauta(pauta);

		when(votacaoService.buildVotacaoPauta(anyLong())).thenReturn(VotacaoDto.builder().build());

		votoService.verifyVoto(sessao, voto);
	}

	@Test(expected = InvalidCpfException.class)
	public void cpfAbleToVoteTest() {
		Voto voto = new Voto();
		voto.setCpf("1234");

		CpfValidationDto cpf = new CpfValidationDto();
		cpf.setStatus("TESTE");

		ResponseEntity<CpfValidationDto> response = new ResponseEntity<>(cpf, HttpStatus.NOT_FOUND);

		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(CpfValidationDto.class)))
				.thenReturn(response);

		votoService.cpfAbleToVote(voto);
	}

	@Test(expected = UnableCpfException.class)
	public void cpfAbleToVote2Test() {
		Voto voto = new Voto();
		voto.setCpf("1234");

		CpfValidationDto cpf = new CpfValidationDto();
		cpf.setStatus("UNABLE_TO_VOTE");

		ResponseEntity<CpfValidationDto> response = new ResponseEntity<>(cpf, HttpStatus.OK);

		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(CpfValidationDto.class)))
				.thenReturn(response);

		votoService.cpfAbleToVote(voto);
	}

	@Test
	public void cpfAbleToVote3Test() {
		Voto voto = new Voto();
		voto.setCpf("1234");

		CpfValidationDto cpf = new CpfValidationDto();
		cpf.setStatus("ABLE_TO_VOTE");

		ResponseEntity<CpfValidationDto> response = new ResponseEntity<>(cpf, HttpStatus.OK);

		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(CpfValidationDto.class)))
				.thenReturn(response);

		votoService.cpfAbleToVote(voto);
	}

	@Test(expected = VotoAlreadyExistsException.class)
	public void votoAlreadyExistsTest() {
		Voto voto = new Voto();
		voto.setCpf("1234");
		Pauta pauta = new Pauta();
		pauta.setId(1L);
		voto.setPauta(pauta );
		when(votoRepository.findByCpfAndPautaId(anyString(), anyLong())).thenReturn(Optional.of(new Voto()));
		votoService.votoAlreadyExists(voto);
	}
	
	@Test
	public void votoAlreadyExistssTest() {
		Voto voto = new Voto();
		voto.setCpf("1234");
		Pauta pauta = new Pauta();
		pauta.setId(1L);
		voto.setPauta(pauta );
		
		when(votoRepository.findByCpfAndPautaId(anyString(), anyLong())).thenReturn(Optional.empty());
		votoService.votoAlreadyExists(voto);
	}

}
