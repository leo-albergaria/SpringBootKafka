package io.github.leo_albergaria.icompras.pedidos.controller.dto;

/**
 * Body:
 * {
 *   "codigo": "number",
 *   "chavePagamento": "string",
 *   "status": "boolean",
 *   "observacoes": "string"
 * }
 *
 * Headers:
 * {
 *   "apiKey": "string"
 * }
 */

public record RecebimentoCallbackPagamentoDTO(
        Long codigo, String chavePagamento, boolean status, String observacoes
) {
}
