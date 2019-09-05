package br.com.votacao.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class CpfValidationDto implements Serializable {
	private static final long serialVersionUID = 633031857370234293L;
	
	private String status;
}
