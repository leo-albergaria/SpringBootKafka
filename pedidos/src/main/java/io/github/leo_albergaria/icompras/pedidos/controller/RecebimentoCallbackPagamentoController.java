package io.github.leo_albergaria.icompras.pedidos.controller;


import io.github.leo_albergaria.icompras.pedidos.controller.dto.RecebimentoCallbackPagamentoDTO;
import io.github.leo_albergaria.icompras.pedidos.model.Pedido;
import io.github.leo_albergaria.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos/callback-pagamentos")
@RequiredArgsConstructor
public class RecebimentoCallbackPagamentoController {

    public final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Object> atualizarStatusPagamento(
            @RequestBody RecebimentoCallbackPagamentoDTO recebimentoCallbackPagamentoDTO,
            @RequestHeader(required = true, name = "apiKey") String apiKey) {

        pedidoService.atualizarStatusPagamento(recebimentoCallbackPagamentoDTO, apiKey);
        return ResponseEntity.ok().build();
    }

}
