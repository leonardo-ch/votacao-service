package br.com.votacao.domain;

import br.com.votacao.model.Pauta;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class VotacaoDto implements Serializable {
	private static final long serialVersionUID = -6641295645471857940L;
	
	private Pauta pauta;
	private Integer totalSim;
	private Integer totalNao;
	private Integer totalVotos;
	private Integer totalSessoes;
}
