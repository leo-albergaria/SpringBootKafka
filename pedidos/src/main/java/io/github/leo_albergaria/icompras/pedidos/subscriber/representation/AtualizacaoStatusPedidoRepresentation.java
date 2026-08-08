package io.github.leo_albergaria.icompras.pedidos.subscriber.representation;

import io.github.leo_albergaria.icompras.pedidos.model.enums.StatusPedido;

public record AtualizacaoStatusPedidoRepresentation(
        Long codigo, StatusPedido status, String urlNotaFiscal, String codigoRastreio) {
}