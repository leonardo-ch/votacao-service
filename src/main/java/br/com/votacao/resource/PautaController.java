package br.com.votacao.resource;

import br.com.votacao.model.Pauta;
import br.com.votacao.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PautaController {

	private final PautaService pautaService;

	@Autowired
	public PautaController(PautaService pautaService) {
		this.pautaService = pautaService;
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
}
