package io.github.leo_albergaria.icompras.pedidos.validator;

import io.github.leo_albergaria.icompras.pedidos.client.ClientesClient;
import io.github.leo_albergaria.icompras.pedidos.client.ProdutosClient;
import io.github.leo_albergaria.icompras.pedidos.client.representation.ProdutoRepresentation;
import io.github.leo_albergaria.icompras.pedidos.model.ItemPedido;
import io.github.leo_albergaria.icompras.pedidos.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PedidoValidator {

    private final ProdutosClient produtosClient;
    private final ClientesClient clientesClient;

    public void validarPedido(Pedido pedido) {

        Long codigoCliente = pedido.getCodigoCliente();
        validarCliente(codigoCliente);
        pedido.getItens().forEach(this::validarProdutos);
    }

    public void validarCliente(Long codigoCliente) {
        // Implementação da validação do cliente
    }

    public void validarProdutos(ItemPedido itemPedido) {
        // Implementação da validação dos produtos
    }

}
