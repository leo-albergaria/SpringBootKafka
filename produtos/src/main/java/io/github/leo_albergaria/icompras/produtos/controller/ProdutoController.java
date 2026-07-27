package io.github.leo_albergaria.icompras.produtos.controller;

import io.github.leo_albergaria.icompras.produtos.model.Produto;
import io.github.leo_albergaria.icompras.produtos.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto) {
        Produto produtoSalvo = service.salvar(produto);
        return ResponseEntity.ok(produtoSalvo);
    }

    @GetMapping("{codigo}")
    public ResponseEntity<Produto> obterDados(@PathVariable("codigo") Long codigo){
        return service.obterPorProduto(codigo).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

}
