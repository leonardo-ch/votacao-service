package br.com.votacao.kafka;

import br.com.votacao.domain.VotacaoDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    private final KafkaTemplate<String, VotacaoDto> kafkaTemplate;

    public KafkaSender(KafkaTemplate<String, VotacaoDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(VotacaoDto votacaoDto) {
        kafkaTemplate.send("votacao", votacaoDto);
    }
}
