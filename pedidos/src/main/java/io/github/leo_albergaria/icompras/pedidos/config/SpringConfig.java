//package io.github.leo_albergaria.icompras.pedidos.config;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import tools.jackson.databind.DeserializationFeature;
//import tools.jackson.databind.ObjectMapper;
//import tools.jackson.databind.json.JsonMapper;
//
//@Configuration
//public class SpringConfig {
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        return JsonMapper.builder()
//                // Substitui o mapper.setSerializationInclusion(...)
//                .changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(JsonInclude.Include.NON_NULL))
//
//                // Substitui o mapper.configure(..., false)
//                .disable(DeserializationFeature.FAIL_ON_UNEXPECTED_VIEW_PROPERTIES)
//
//                // Nota: Jdk8Module e JavaTimeModule já são registados nativamente!
//                .build();
//    }
//}

package io.github.leo_albergaria.icompras.pedidos.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}