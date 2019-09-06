package br.com.votacao.controller;

import br.com.votacao.domain.VotacaoDto;
import br.com.votacao.service.VotacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Votacao")
public class VotacaoController {

	private final VotacaoService votacaoService;

	public VotacaoController(VotacaoService votacaoService) {
		this.votacaoService = votacaoService;
	}

	@ApiOperation(value = "Get Resultado Votação")
	@GetMapping("v1/pautas/{id}/votacao")
	@ResponseStatus(code = HttpStatus.OK)
	public VotacaoDto findVotosByPautaId(@PathVariable Long id) {
		return votacaoService.getResultVotacao(id);
	}
}
