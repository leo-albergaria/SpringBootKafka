package io.github.leo_albergaria.icompras.faturamento.config;

import io.github.leo_albergaria.icompras.faturamento.config.props.MinioProps;
import io.minio.MinioClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MinioProps.class)
public class BucketConfig {

    @Bean
    public MinioClient bucketClient(MinioProps props) {
        return MinioClient.builder()
                .endpoint(props.getUrl())
                .credentials(props.getAccessKey(), props.getSecretKey())
                .build();
    }
}