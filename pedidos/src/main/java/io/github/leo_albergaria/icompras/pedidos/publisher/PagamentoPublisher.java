package io.github.leo_albergaria.icompras.pedidos.publisher;

import io.github.leo_albergaria.icompras.pedidos.model.Pedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class PagamentoPublisher {

    private final DetalhePedidoMapper mapper;
    private final ObjectMapper mapperJson;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${icompras.config.kafka.topics.pedidos-pagos}")
    private String topic;

    public void publicar(Pedido pedido) {
        log.info("[PUBLICAR] Pedido recebido com sucesso {}", pedido.getCodigo());

        try {
            var representation = mapper.map(pedido);
            var json = mapperJson.writeValueAsString(representation);
            kafkaTemplate.send(topic, "dados", json);
        } catch (RuntimeException e) {
            log.error("[PUBLICAR] Erro ao serializar JSON do pedido {}", pedido.getCodigo(), e);
        } catch (Exception e) { // Captura qualquer outro erro de envio do Kafka ou runtime
            log.error("[PUBLICAR] Erro ao publicar topico do pedido {}", pedido.getCodigo(), e);
        }
    }
}
