package io.github.leo_albergaria.icompras.clientes.repository;

import io.github.leo_albergaria.icompras.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {


}
