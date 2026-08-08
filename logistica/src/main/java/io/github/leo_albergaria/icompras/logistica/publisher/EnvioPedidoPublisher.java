package io.github.leo_albergaria.icompras.logistica.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.leo_albergaria.icompras.logistica.model.AtualizacaoEnvioPedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnvioPedidoPublisher {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${icompras.config.kafka.topics.pedidos-enviados}")
    private String topico;

    public void enviar(AtualizacaoEnvioPedido atualizacaoEnvioPedido) {
        log.info("Publicando mensagem de envio do pedido: {}", atualizacaoEnvioPedido);

        try {
            var json = objectMapper.writeValueAsString(atualizacaoEnvioPedido);
            kafkaTemplate.send( topico, "dados", json);
            log.info("Publicado o pedido: {}, codigo de rastreio: {}", atualizacaoEnvioPedido.codigo(), atualizacaoEnvioPedido.codigoRastreio());
        } catch (Exception e) {
            log.error("Erro ao publicar mensagem de envio do pedido: {}", atualizacaoEnvioPedido.codigo(), e);
        }
    }
}
