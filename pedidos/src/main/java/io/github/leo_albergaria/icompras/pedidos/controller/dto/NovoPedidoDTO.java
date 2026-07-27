package io.github.leo_albergaria.icompras.pedidos.controller.dto;

import java.util.List;

public record NovoPedidoDTO(Long codigoCliente, DadosPagamentoDTO dadosPagamento, List<ItemPedidoDTO> itens) {
}
