package io.github.leo_albergaria.icompras.faturamento.bucket;

import io.github.leo_albergaria.icompras.faturamento.config.props.MinioProps;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class BucketService {

    private final MinioClient minioClient;
    private final MinioProps minioProps;

    public void upload(BucketFile bucketFile) {
        try {
            var object = PutObjectArgs
                    .builder()
                    .bucket(minioProps.getBucketName())
                    .object(bucketFile.name())
                    .stream(bucketFile.is(), bucketFile.size(), -1)
                    .contentType(bucketFile.type().toString())
                    .build();
            minioClient.putObject(object);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar arquivo para o MinIO", e);
        }
    }

    public String getUrl(String fileName) {
        try {

            String encodedFileName = encodePath(fileName);

            var object = GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(minioProps.getBucketName())
                    .object(encodedFileName)
                    .expiry(7, TimeUnit.DAYS)
                    .build();
            return minioClient.getPresignedObjectUrl(object);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter URL do arquivo no MinIO", e);
        }
    }

    /**
     * Método utilitário para codificar o nome do arquivo/caminho sem quebrar as barras '/'.
     */
    private String encodePath(String path) {
        if (path == null) return null;

        // Se o caminho tiver subpastas (ex: "2026/fotos/foto+1.jpg"),
        // codificamos cada parte individualmente para não transformar a '/' em '%2F'
        String[] parts = path.split("/");
        StringBuilder encodedPath = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            String encodedPart = URLEncoder.encode(parts[i], StandardCharsets.UTF_8)
                    .replace("+", "%2B"); // ⚠️ Garante que o '+' vira '%2B' e não continua como '+'

            encodedPath.append(encodedPart);
            if (i < parts.length - 1) {
                encodedPath.append("/");
            }
        }

        return encodedPath.toString();
    }

}
