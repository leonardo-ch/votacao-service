package br.com.votacao.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode
@Builder
public class Sessao implements Serializable {
    @Id
    private Long id;

    private LocalDateTime dataInicio;

    private Long minutosValidade;

    @ManyToOne(fetch = FetchType.EAGER)
    private Pauta pauta;

    public Sessao pauta(Pauta pauta) {
        this.pauta = pauta;
        return this;
    }
}
