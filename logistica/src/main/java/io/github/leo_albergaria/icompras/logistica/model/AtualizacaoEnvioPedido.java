package io.github.leo_albergaria.icompras.logistica.model;

public record AtualizacaoEnvioPedido (
    Long codigo,
    StatusPedido status,
    String codigoRastreio
) {
}
