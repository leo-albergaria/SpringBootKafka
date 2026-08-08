package io.github.leo_albergaria.icompras.faturamento;

import io.github.leo_albergaria.icompras.faturamento.bucket.BucketFile;
import io.github.leo_albergaria.icompras.faturamento.bucket.BucketService;
import io.github.leo_albergaria.icompras.faturamento.model.Pedido;
import io.github.leo_albergaria.icompras.faturamento.publisher.FaturamentoPublisher;
import io.github.leo_albergaria.icompras.faturamento.service.NotaFiscalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeradorNotaFiscalService {

    private final NotaFiscalService notaFiscalService;
    private final BucketService bucketService;
    private final FaturamentoPublisher faturamentoPublisher;

    public void gerar(Pedido pedido) {
        log.info("Gerando nota fiscal para o pedido: {}", pedido.codigo());

        try {
            byte[] byteArray = notaFiscalService.gerarNotaFiscal(pedido);
            String nomeArquivo = String.format("nota-fiscal-%s.pdf", pedido.codigo());

            var file = new BucketFile(nomeArquivo, new ByteArrayInputStream(byteArray), MediaType.APPLICATION_PDF, byteArray.length);

            bucketService.upload(file);

            String url = bucketService.getUrl(nomeArquivo);
            faturamentoPublisher.publish(pedido, url);

            log.info("Arquivo gerado com sucesso: {}", nomeArquivo);
        } catch (Exception e) {
            log.error("Erro ao gerar nota fiscal: {}", e.getMessage());
        }
    }
}
