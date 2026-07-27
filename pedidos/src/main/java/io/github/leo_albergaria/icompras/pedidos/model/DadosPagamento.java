package io.github.leo_albergaria.icompras.pedidos.model;

import io.github.leo_albergaria.icompras.pedidos.model.enums.TipoPagamento;
import lombok.Data;

@Data
public class DadosPagamento {
    private String dados;
    private TipoPagamento tipoPagamento;
}
