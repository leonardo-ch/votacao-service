package br.com.votacao.repository;

import br.com.votacao.model.Sessao;
import org.hibernate.validator.constraints.EAN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    Optional<Sessao> findByIdAndPautaId(Long id, Long pautaId);

    Long countByPautaId(Long id);

    Optional<List<Sessao>> findByPautaId(Long id);
}
