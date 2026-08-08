package io.github.leo_albergaria.icompras.pedidos.controller.dto;

import io.github.leo_albergaria.icompras.pedidos.model.enums.TipoPagamento;

public record AdicionarNovoPagamentoDTO(Long codigoPedido, String dados, TipoPagamento tipoPagamento) {
}
