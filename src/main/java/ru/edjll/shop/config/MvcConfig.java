package ru.edjll.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${manufacturer.upload.path}")
    private String manufacturerUploadPath;

    @Value("${promotion.upload.path}")
    private String promotionUploadPath;

    @Value("${productData.upload.path}")
    private String productDataUploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/image/manufacturerLogo/**")
                .addResourceLocations("file://" + manufacturerUploadPath + "/");
        registry.addResourceHandler("/image/promotionBanner/**")
                .addResourceLocations("file://" + promotionUploadPath + "/");
        registry.addResourceHandler("/image/productDataImage/**")
                .addResourceLocations("file://" + productDataUploadPath + "/");
    }

    @Bean
    public RestTemplate restTemplate() {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        return new RestTemplate(factory);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        converters.add(jsonConverter);
    }
}