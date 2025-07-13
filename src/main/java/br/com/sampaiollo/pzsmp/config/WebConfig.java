package br.com.sampaiollo.pzsmp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Este método diz ao Spring: "Quando uma requisição chegar para /product-images/**,
        // procure pelo arquivo correspondente na pasta física 'product-images/' no seu projeto."
        registry.addResourceHandler("/product-images/**")
                .addResourceLocations("file:product-images/");
    }
}