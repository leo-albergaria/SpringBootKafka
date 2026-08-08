package io.github.leo_albergaria.icompras.pedidos.validator;

import feign.FeignException;
import io.github.leo_albergaria.icompras.pedidos.client.ClientesClient;
import io.github.leo_albergaria.icompras.pedidos.client.ProdutosClient;
import io.github.leo_albergaria.icompras.pedidos.client.representation.ClienteRepresentation;
import io.github.leo_albergaria.icompras.pedidos.client.representation.ProdutoRepresentation;
import io.github.leo_albergaria.icompras.pedidos.model.ItemPedido;
import io.github.leo_albergaria.icompras.pedidos.model.Pedido;
import io.github.leo_albergaria.icompras.pedidos.model.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoValidator {

    private final ProdutosClient produtosClient;
    private final ClientesClient clientesClient;

    public void validarPedido(Pedido pedido) {

        Long codigoCliente = pedido.getCodigoCliente();
        validarCliente(codigoCliente);
        pedido.getItens().forEach(this::validarProdutos);
    }

    public void validarCliente(Long codigoCliente) {
        try {
            var response = clientesClient.obterDados(codigoCliente);
            ClienteRepresentation cliente = response.getBody();
            log.info("Cliente Obter Dados: {}", cliente);
        } catch (FeignException.NotFound e) {
            var message = String.format("Cliente de codigo %d não encontrado", codigoCliente);
            throw new ValidationException("codigoCliente", message);
        }
    }

    public void validarProdutos(ItemPedido itemPedido) {
        try {
            var response = produtosClient.obterDados(itemPedido.getCodigoProduto());
            ProdutoRepresentation produto = response.getBody();
            log.info("Produto Obter Dados: {}", produto);
        } catch (FeignException.NotFound e) {
            var message = String.format("Produto de codigo %d não encontrado", itemPedido.getCodigoProduto());
            throw new ValidationException("codigoProduto", message);
        }
    }

}
