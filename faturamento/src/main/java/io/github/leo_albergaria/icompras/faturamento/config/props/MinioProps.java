package io.github.leo_albergaria.icompras.faturamento.config.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "minio")
@Getter
@Setter
public class MinioProps {

    private String url;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}