package io.github.leo_albergaria.icompras.pedidos.service;

import io.github.leo_albergaria.icompras.pedidos.model.enums.StatusPedido;
import io.github.leo_albergaria.icompras.pedidos.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtualizacaoStatusPedidoService {

    private final PedidoRepository pedidoRepository;

    @Transactional
    public void atualizarStatus(Long codigo, StatusPedido status, String urlNotaFiscal, String codigoRastreio) {

        pedidoRepository.findById(codigo).ifPresent(pedido -> {
            pedido.setStatus(status);
            pedido.setCodigoRastreio(codigoRastreio);
            if (urlNotaFiscal != null) {pedido.setUrlNotaFiscal(urlNotaFiscal);}
        });
        log.info("AtualizacaoStatusPedidoService.atualizarStatus - Pedido atualizado: {}, Status: {}, URL Nota Fiscal: {}, Código de Rastreio: {}", codigo, status, urlNotaFiscal, codigoRastreio);
    }

}
