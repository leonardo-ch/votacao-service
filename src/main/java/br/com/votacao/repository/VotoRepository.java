package br.com.votacao.repository;

import br.com.votacao.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    Optional<Voto> findByCpf(String cpf);

    Optional<List<Voto>> findByPautaId(Long id);

    Optional<Voto> findByCpfAndPautaId(String cpf, Long id);
}
