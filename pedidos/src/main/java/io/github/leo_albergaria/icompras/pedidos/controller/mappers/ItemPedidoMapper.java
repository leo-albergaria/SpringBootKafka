package io.github.leo_albergaria.icompras.pedidos.controller.mappers;

import io.github.leo_albergaria.icompras.pedidos.controller.dto.ItemPedidoDTO;
import io.github.leo_albergaria.icompras.pedidos.model.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemPedidoMapper {

    ItemPedido map(ItemPedidoDTO itemPedidoDTO);

}
