package br.com.votacao.controller;

import br.com.votacao.model.Voto;
import br.com.votacao.service.VotoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VotoController {

	private final VotoService votoService;

	public VotoController(VotoService votoService) {
		this.votoService = votoService;
	}

	@GetMapping("v1/pautas/sessoes/votos")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Voto> all() {
		return votoService.findAll();
	}

	@PostMapping("v1/pautas/{idPauta}/sessoes/{idSessao}/votos")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Voto createVoto(@PathVariable Long idPauta, @PathVariable Long idSessao, @RequestBody Voto voto) {
		return votoService.createVoto(idPauta, idSessao, voto);
	}

	@GetMapping("v1/pautas/sessoes/votos/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Voto findVotoById(@PathVariable Long id) {
		return votoService.findById(id);
	}

	@GetMapping("v1/pautas/{id}/sessoes/votos")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Voto> findVotoBySessaoId(@PathVariable Long id) {
		return votoService.findVotosByPautaId(id);
	}

	@DeleteMapping("v1/pautas/sessoes/votos/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void delete(@PathVariable Long id) {
		votoService.delete(id);
	}
}
