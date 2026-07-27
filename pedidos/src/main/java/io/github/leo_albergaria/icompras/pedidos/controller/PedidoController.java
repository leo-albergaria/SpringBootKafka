package io.github.leo_albergaria.icompras.pedidos.controller;

import io.github.leo_albergaria.icompras.pedidos.controller.dto.NovoPedidoDTO;
import io.github.leo_albergaria.icompras.pedidos.controller.mappers.PedidoMapper;
import io.github.leo_albergaria.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    @PostMapping
    public ResponseEntity<Object> criarPedido(@RequestBody NovoPedidoDTO novoPedidoDTO) {

        var pedido = pedidoMapper.map(novoPedidoDTO);
        var novoPedido = pedidoService.criarPedido(pedido);
        return ResponseEntity.ok(novoPedido.getCodigo());
    }


}
