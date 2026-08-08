package io.github.leo_albergaria.icompras.faturamento.service;

import io.github.leo_albergaria.icompras.faturamento.model.Pedido;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotaFiscalService {


    @Value("classpath:reports/nota-fiscal.jrxml")
    private Resource notaFiscal;

    @Value("classpath:reports/logo.png")
    private Resource logo;

    public byte[] gerarNotaFiscal(Pedido pedido) {

        try (InputStream inputStream = notaFiscal.getInputStream()) {

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("LOGO", logo.getFile().getAbsolutePath());
            parameters.put("NOME", pedido.cliente().nome());
            parameters.put("CPF", pedido.cliente().cpf());
            parameters.put("LOGRADOURO", pedido.cliente().logradouro());
            parameters.put("NUMERO", pedido.cliente().numero());
            parameters.put("BAIRRO", pedido.cliente().bairro());
            parameters.put("EMAIL", pedido.cliente().email());
            parameters.put("TELEFONE", pedido.cliente().telefone());
            parameters.put("DATA_PEDIDO", pedido.data());
            parameters.put("TOTAL_PEDIDO", pedido.total());

            var dataSource = new JRBeanCollectionDataSource(pedido.itens());

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar nota fiscal", e);
        }
    }
}
