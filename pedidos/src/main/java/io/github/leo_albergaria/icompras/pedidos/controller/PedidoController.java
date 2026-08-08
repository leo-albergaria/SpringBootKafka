package io.github.leo_albergaria.icompras.pedidos.controller;

import io.github.leo_albergaria.icompras.pedidos.controller.dto.AdicionarNovoPagamentoDTO;
import io.github.leo_albergaria.icompras.pedidos.controller.dto.NovoPedidoDTO;
import io.github.leo_albergaria.icompras.pedidos.controller.mappers.PedidoMapper;
import io.github.leo_albergaria.icompras.pedidos.model.ErroResposta;
import io.github.leo_albergaria.icompras.pedidos.model.exception.ItemNaoEncontradoException;
import io.github.leo_albergaria.icompras.pedidos.model.exception.ValidationException;
import io.github.leo_albergaria.icompras.pedidos.publisher.DetalhePedidoMapper;
import io.github.leo_albergaria.icompras.pedidos.publisher.representation.DetalhePedidoRepresentation;
import io.github.leo_albergaria.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final DetalhePedidoMapper detalhePedidoMapper;

    @PostMapping
    public ResponseEntity<Object> criarPedido(@RequestBody NovoPedidoDTO novoPedidoDTO) {

        try {
            var pedido = pedidoMapper.map(novoPedidoDTO);
            var novoPedido = pedidoService.criarPedido(pedido);
            return ResponseEntity.ok(novoPedido.getCodigo());
        } catch (ValidationException e) {
            var erro = new ErroResposta("Erro validação: ", e.getField(), e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    @PostMapping("pagamentos")
    public ResponseEntity<Object> adicionarNovoPagamento(@RequestBody AdicionarNovoPagamentoDTO dto ) {

        try {
            pedidoService.adicionarNovoPagamento(dto.codigoPedido(), dto.dados(), dto.tipoPagamento());
            return ResponseEntity.noContent().build();
        } catch (ItemNaoEncontradoException e) {
            var erro = new ErroResposta("Item não econtrado. ", "codigoPedido", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    @GetMapping("{codigo}")
    public ResponseEntity<DetalhePedidoRepresentation> obterDetalhesPedido(@PathVariable Long codigo) {
        return pedidoService
                .carregarDadosCompletosPedido(codigo)
                .map(detalhePedidoMapper::map)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
