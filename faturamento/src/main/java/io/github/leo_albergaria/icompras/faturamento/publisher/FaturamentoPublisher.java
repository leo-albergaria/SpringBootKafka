package io.github.leo_albergaria.icompras.faturamento.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.leo_albergaria.icompras.faturamento.model.Pedido;
import io.github.leo_albergaria.icompras.faturamento.publisher.representation.AtualizacaoStatusPedido;
import io.github.leo_albergaria.icompras.faturamento.publisher.representation.StatusPedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FaturamentoPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${icompras.config.kafka.topic.pedidos-faturados}")
    private String topicPedidosFaturados;

    public void publish(Pedido pedido, String urlNotaFiscal) {
        try {
            var representation = new AtualizacaoStatusPedido(pedido.codigo(), StatusPedido.FATURADO, urlNotaFiscal);

            String json = objectMapper.writeValueAsString(representation);
            kafkaTemplate.send(topicPedidosFaturados, "dados", json);

        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }
}



