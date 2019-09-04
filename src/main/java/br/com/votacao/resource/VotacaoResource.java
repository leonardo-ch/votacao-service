package br.com.votacao.resource;

import br.com.votacao.model.Pauta;
import br.com.votacao.model.Sessao;
import br.com.votacao.service.PautaService;
import br.com.votacao.service.SessaoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class VotacaoResource {

	private final PautaService pautaService;
	private final SessaoService sessaoService;

	public VotacaoResource(PautaService pautaService, SessaoService sessaoService) {
		this.pautaService = pautaService;
		this.sessaoService = sessaoService;
	}

	@GetMapping("v1/pautas")
	public List<Pauta> all() {
		return pautaService.findAll();
	}
	
	@PostMapping("v1/pautas")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Pauta create(@Valid @RequestBody Pauta pauta) {
		return pautaService.save(pauta);
	}
	
	@GetMapping("v1/pautas/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Pauta findById(@PathVariable Long id) {
		return pautaService.findById(id);
	}

	@PostMapping("v1/pautas/{id}/sessoes")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Sessao createSession(@PathVariable Long id, @Valid @RequestBody Sessao sessao) {
		return sessaoService.createSession(id,sessao);
	}

	@GetMapping("v1/pautas/sessoes/{idSessao}")
	@ResponseStatus(code = HttpStatus.OK)
	public Sessao findSessaoById(@PathVariable Long id) {
		return sessaoService.findById(id);
	}

	@GetMapping("v1/pautas/{id}/sessoes/{idSessao}")
	@ResponseStatus(code = HttpStatus.OK)
	public Sessao findSessaoById(@PathVariable Long id, @PathVariable Long idSessao) {
		return sessaoService.findByIdAndPautaId(idSessao, id);
	}
}
