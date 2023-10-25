package com.coremedia.commerce.adapter.commercelayer.configuration;

import com.coremedia.commerce.adapter.commercelayer.CommerceLayerApiConnector;
import com.coremedia.commerce.adapter.commercelayer.api.resources.MarketsResource;
import com.coremedia.commerce.adapter.commercelayer.api.resources.SKUResource;
import com.coremedia.commerce.adapter.commercelayer.oauth.CommerceLayerAuthenticator;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties({
        CommerceLayerApiConfigurationProperties.class
})
public class CommerceLayerConnectorConfiguration {

    @Bean
    public CommerceLayerApiConnector commerceLayerApiConnector(@NonNull CommerceLayerApiConfigurationProperties properties,
                                                               @NonNull CommerceLayerAuthenticator commerceLayerAuthenticator,
                                                               @NonNull RestTemplate restTemplate) {
        return new CommerceLayerApiConnector(properties, commerceLayerAuthenticator, restTemplate);
    }

    @Bean
    public CommerceLayerAuthenticator commerceLayerAuthenticator(@NonNull CommerceLayerApiConfigurationProperties properties,
                                                                 @NonNull RestTemplateBuilder restTemplateBuilder) {
        return new CommerceLayerAuthenticator(properties, restTemplateBuilder);
    }

    @Bean
    public RestTemplate restTemplate(@NonNull CommerceLayerAuthenticator commerceLayerAuthenticator) {
        RestTemplate restTemplate = new RestTemplate();

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(commerceLayerAuthenticator);
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }


    // --- Commerce Hub Repositories ---
    @Bean
    public SKUResource skuResource(@NonNull CommerceLayerApiConnector apiConnector) {
        return new SKUResource(apiConnector);
    }
    @Bean
    public MarketsResource marketsResource(@NonNull CommerceLayerApiConnector apiConnector) {
        return new MarketsResource(apiConnector);
    }

}
