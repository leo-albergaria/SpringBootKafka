package io.github.leo_albergaria.icompras.clientes.controller;

import io.github.leo_albergaria.icompras.clientes.model.Cliente;
import io.github.leo_albergaria.icompras.clientes.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente) {
        Cliente clienteSalvo = clienteService.salvar(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    @GetMapping("{codigo}")
    public ResponseEntity<Cliente> obterDados(@PathVariable("codigo") Long codigo){
        return clienteService
                .obterPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
