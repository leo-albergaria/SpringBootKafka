package io.github.leo_albergaria.icompras.pedidos.controller.mappers;

import io.github.leo_albergaria.icompras.pedidos.controller.dto.ItemPedidoDTO;
import io.github.leo_albergaria.icompras.pedidos.controller.dto.NovoPedidoDTO;
import io.github.leo_albergaria.icompras.pedidos.model.ItemPedido;
import io.github.leo_albergaria.icompras.pedidos.model.Pedido;
import io.github.leo_albergaria.icompras.pedidos.model.enums.StatusPedido;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PedidoMapper {

    ItemPedidoMapper ITEM_PEDIDO_MAPPER = Mappers.getMapper(ItemPedidoMapper.class);

    @Mapping(source = "itens", target = "itens", qualifiedByName = "mapItens")
    @Mapping(source = "dadosPagamento", target = "dadosPagamento")
    Pedido map(NovoPedidoDTO novoPedidoDTO);

    @Named("mapItens")
    default List<ItemPedido> mapItens(List<ItemPedidoDTO> itemPedidoDTO) {
        return itemPedidoDTO.stream().map(ITEM_PEDIDO_MAPPER::map).toList();
    }

    @AfterMapping
    default void afterMapping(@MappingTarget Pedido pedido) {
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setDataPedido(LocalDateTime.now());

        var total = calcularTotal(pedido);
        pedido.setTotal(total);

        pedido.getItens().forEach(item -> item.setPedido(pedido));
    }

    private static BigDecimal calcularTotal(Pedido pedido) {
        return pedido.getItens().stream().map(item -> item.getValorUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()))).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
