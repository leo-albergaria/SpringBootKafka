package io.github.leo_albergaria.icompras.pedidos.repository;

import io.github.leo_albergaria.icompras.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
