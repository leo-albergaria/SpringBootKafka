package io.github.leo_albergaria.icompras.faturamento.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.leo_albergaria.icompras.faturamento.GeradorNotaFiscalService;
import io.github.leo_albergaria.icompras.faturamento.mapper.PedidoMapper;
import io.github.leo_albergaria.icompras.faturamento.model.Pedido;
import io.github.leo_albergaria.icompras.faturamento.subscriber.representation.DetalhePedidoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PedidoPagoSubscriber {

    private final ObjectMapper objectMapper;
    private final GeradorNotaFiscalService geradorNotaFiscalService;
    private final PedidoMapper pedidoMapper;

    @KafkaListener(groupId = "icompras-faturamento", topics = "${icompras.config.kafka.topic.pedidos-pagos}")
    public void listen(String json){

        try {
            log.info("Pedido pago recebido: {}",json);
            var representation = objectMapper.readValue(json, DetalhePedidoRepresentation.class);
            Pedido pedido = pedidoMapper.map(representation);
            geradorNotaFiscalService.gerar(pedido);
        } catch (Exception e) {
            log.error("Erro ao processar pedido pago: {}", e.getMessage(), e);
        }
    }
}
