package br.com.votacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode
@Builder
public class Voto implements Serializable {
    @Id
    private Long id;

    @NotBlank(message = "voto-1")
    private String cpf;

    @NotNull(message = "voto-2")
    private Boolean escolha;

    @NotNull(message = "voto-3")
    @ManyToOne(fetch = FetchType.EAGER)
    private Pauta pauta;

    @JsonIgnore
    public boolean isNew() {
        return getId() == null;
    }

    @JsonIgnore
    public boolean alreadyExist() {
        return getId() != null;
    }
}
