package io.github.leo_albergaria.icompras.pedidos.service;

import io.github.leo_albergaria.icompras.pedidos.client.ServicoBancarioClient;
import io.github.leo_albergaria.icompras.pedidos.model.Pedido;
import io.github.leo_albergaria.icompras.pedidos.repository.ItemPedidoRepository;
import io.github.leo_albergaria.icompras.pedidos.repository.PedidoRepository;
import io.github.leo_albergaria.icompras.pedidos.validator.PedidoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator pedidoValidator;
    private final ServicoBancarioClient servicoBancarioClient;

    @Transactional
    public Pedido criarPedido(Pedido pedido) {

        pedidoValidator.validarPedido(pedido);
        realizarPersistencia(pedido);
        enviarSolicitacaoPagamento(pedido);

        return pedido;
    }

    private void enviarSolicitacaoPagamento(Pedido pedido) {
        var chavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(chavePagamento);
    }

    private void realizarPersistencia(Pedido pedido) {
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }
}
