package br.com.votacao.repository;

import br.com.votacao.model.Pauta;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.jpa.repository.*;

@EnableJpaRepositories
public interface PautaRepository extends JpaRepository<Pauta, Long> {
}
