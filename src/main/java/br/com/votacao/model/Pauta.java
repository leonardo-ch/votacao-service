package br.com.votacao.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Entity
@EqualsAndHashCode
@Builder
public class Pauta implements Serializable {
    @Id
    private Long id;

    @NotBlank(message = "pauta-1")
    private String nome;
}
