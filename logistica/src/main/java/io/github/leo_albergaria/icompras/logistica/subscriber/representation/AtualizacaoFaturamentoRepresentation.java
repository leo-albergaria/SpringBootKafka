package io.github.leo_albergaria.icompras.logistica.subscriber.representation;

import io.github.leo_albergaria.icompras.logistica.model.StatusPedido;

public record AtualizacaoFaturamentoRepresentation(
        Long codigo, StatusPedido status, String urlNotaFiscal
) {
}
