package io.github.leo_albergaria.icompras.logistica.service;

import io.github.leo_albergaria.icompras.logistica.model.AtualizacaoEnvioPedido;
import io.github.leo_albergaria.icompras.logistica.model.StatusPedido;
import io.github.leo_albergaria.icompras.logistica.publisher.EnvioPedidoPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnvioPedidoService {

    private final EnvioPedidoPublisher publisher;

    public void enviar(Long codigoPedido, String urlNotaFiscal){

        var codigoRastreio = gerarCodigoRastreio();
        var atualizacaoRepresentation = new AtualizacaoEnvioPedido(codigoPedido, StatusPedido.ENVIADO, codigoRastreio);
        publisher.enviar(atualizacaoRepresentation);
        log.info("Envio Pedido enviado com sucesso");
        log.info(codigoRastreio);
    }

    private String gerarCodigoRastreio() {
        // AB1234456789BR

        var random = new Random();

        char letra1 = (char) ('A' + random.nextInt(26));
        char letra2 = (char) ('A' + random.nextInt(26));
        int numero = 100000000 + random.nextInt(900000000);

        var codigo = "" + letra1 + letra2 + numero + "BR";

        log.info("Codigo do Rastreio recebido: {}", codigo);
        return codigo;
    }
}
