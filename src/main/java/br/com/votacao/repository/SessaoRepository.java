package br.com.votacao.repository;

import br.com.votacao.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    Optional<Sessao> findByIdAndPautaId(Long id, Long pautaId);
}
