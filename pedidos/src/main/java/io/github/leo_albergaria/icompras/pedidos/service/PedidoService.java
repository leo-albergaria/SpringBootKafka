package io.github.leo_albergaria.icompras.pedidos.service;

import io.github.leo_albergaria.icompras.pedidos.client.ClientesClient;
import io.github.leo_albergaria.icompras.pedidos.client.ProdutosClient;
import io.github.leo_albergaria.icompras.pedidos.client.ServicoBancarioClient;
import io.github.leo_albergaria.icompras.pedidos.controller.dto.RecebimentoCallbackPagamentoDTO;
import io.github.leo_albergaria.icompras.pedidos.model.DadosPagamento;
import io.github.leo_albergaria.icompras.pedidos.model.ItemPedido;
import io.github.leo_albergaria.icompras.pedidos.model.Pedido;
import io.github.leo_albergaria.icompras.pedidos.model.enums.StatusPedido;
import io.github.leo_albergaria.icompras.pedidos.model.enums.TipoPagamento;
import io.github.leo_albergaria.icompras.pedidos.model.exception.ItemNaoEncontradoException;
import io.github.leo_albergaria.icompras.pedidos.model.exception.ValidationException;
import io.github.leo_albergaria.icompras.pedidos.publisher.PagamentoPublisher;
import io.github.leo_albergaria.icompras.pedidos.repository.ItemPedidoRepository;
import io.github.leo_albergaria.icompras.pedidos.repository.PedidoRepository;
import io.github.leo_albergaria.icompras.pedidos.validator.PedidoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator pedidoValidator;
    private final ServicoBancarioClient servicoBancarioClient;
    private final ClientesClient apiClientes;
    private final ProdutosClient apiProdutos;
    private final PagamentoPublisher pagamentoPublisher;


    @Transactional
    public Pedido criarPedido(Pedido pedido) {

        pedidoValidator.validarPedido(pedido);
        realizarPersistencia(pedido);
        enviarSolicitacaoPagamento(pedido);

        return pedido;
    }

    private void realizarPersistencia(Pedido pedido) {
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }

    private void enviarSolicitacaoPagamento(Pedido pedido) {
        var chavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(chavePagamento);
    }


    public void atualizarStatusPagamento(RecebimentoCallbackPagamentoDTO recebimentoCallbackPagamentoDTO, String apiKey) {
        var dto = recebimentoCallbackPagamentoDTO;

        log.info("recebimentoCallbackPagamentoDTO: {}", dto);

        var pedido = pedidoRepository.findByCodigoAndChavePagamento(dto.codigo(), dto.chavePagamento())
                .orElseThrow(() -> new ValidationException(
                        "chavePagamento",
                        "Pedido não encontrado para o código: " + dto.codigo() + " e chavePagamento: " + dto.chavePagamento()
                ));

        if(dto.status()) {
            prepararEPublicarPedidoPago(pedido);
        } else {
            pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
            pedido.setObservacoes(dto.observacoes());

        }
        pedidoRepository.save(pedido);
    }

    private void prepararEPublicarPedidoPago(Pedido pedido) {
        pedido.setStatus(StatusPedido.PAGO);
        pedido.setObservacoes("Pagamento realizado com sucesso, aguardando o processamento do pedido.");
        carregarDadosClientes(pedido);
        carregaritensPedido(pedido);
        pagamentoPublisher.publicar(pedido);
    }

    @Transactional
    public void adicionarNovoPagamento(Long codigoPedido, String dadosCartao, TipoPagamento tipo) {

        var pedidoEncontrado = pedidoRepository.findById(codigoPedido);

        if(pedidoEncontrado.isEmpty()) {
          throw new ItemNaoEncontradoException("Pedido não encontrado para o código: " + codigoPedido);
        }

        var pedido = pedidoEncontrado.get();

        DadosPagamento dadosPagamento = new DadosPagamento();
        dadosPagamento.setTipoPagamento(tipo);
        dadosPagamento.setDados(dadosCartao);

        pedido.setDadosPagamento(dadosPagamento);
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setObservacoes("Novo pagamento realizado, aguardando o novo processamento.");

        String novaChavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(novaChavePagamento);

        pedidoRepository.save(pedido);
    }

    public Optional<Pedido> carregarDadosCompletosPedido(Long codigo) {
        Optional<Pedido> pedido = pedidoRepository.findById(codigo);
        pedido.ifPresent(this::carregarDadosClientes);
        pedido.ifPresent(this::carregaritensPedido);
        return pedido;
    }

    private void carregarDadosClientes(Pedido pedido) {
        Long codigoCliente = pedido.getCodigoCliente();
        var response = apiClientes.obterDados(codigoCliente);
        pedido.setDadosCliente(response.getBody());
    }

    private void carregaritensPedido(Pedido pedido) {
        List<ItemPedido> itens = itemPedidoRepository.findByPedido(pedido);
        pedido.setItens(itens);
        pedido.getItens().forEach(this::carregarDadosProdutos);
    }

    private void carregarDadosProdutos(ItemPedido itemPedido) {
        Long codigoProduto = itemPedido.getCodigoProduto();
        var response = apiProdutos.obterDados(codigoProduto);
        itemPedido.setNome(response.getBody().nome());
    }

}
