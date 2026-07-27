package io.github.leo_albergaria.icompras.produtos.repository;

import io.github.leo_albergaria.icompras.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
