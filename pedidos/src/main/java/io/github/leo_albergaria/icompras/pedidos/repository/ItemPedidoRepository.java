package io.github.leo_albergaria.icompras.pedidos.repository;

import io.github.leo_albergaria.icompras.pedidos.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
