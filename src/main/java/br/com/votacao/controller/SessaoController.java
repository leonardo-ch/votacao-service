package br.com.votacao.controller;

import br.com.votacao.model.Sessao;
import br.com.votacao.service.SessaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value = "Sessoes")
public class SessaoController {

	private final SessaoService sessaoService;

	public SessaoController(SessaoService sessaoService) {
		this.sessaoService = sessaoService;
	}

	@ApiOperation(value = "Get Sessoes")
	@GetMapping("v1/pautas/sessoes")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Sessao> all() {
		return sessaoService.findAll();
	}

	@ApiOperation(value = "Create Sessao")
	@PostMapping("v1/pautas/{id}/sessoes")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Sessao createSession(@PathVariable Long id, @Valid @RequestBody Sessao sessao) {
		return sessaoService.createSession(id,sessao);
	}

	@ApiOperation(value = "Find Sessao")
	@GetMapping("v1/pautas/sessoes/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Sessao findSessaoById(@PathVariable Long id) {
		return sessaoService.findById(id);
	}

	@ApiOperation(value = "Find Sessao por Pauta")
	@GetMapping("v1/pautas/{id}/sessoes/{idSessao}")
	@ResponseStatus(code = HttpStatus.OK)
	public Sessao findSessaoByIdAndPautaId(@PathVariable Long id, @PathVariable Long idSessao) {
		return sessaoService.findByIdAndPautaId(idSessao, id);
	}

	@ApiOperation(value = "Delete Sessao")
	@DeleteMapping("v1/pautas/sessoes/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void delete(@PathVariable Long id) {
		sessaoService.delete(id);
	}
}
