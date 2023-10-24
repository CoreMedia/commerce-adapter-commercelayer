package com.coremedia.commerce.adapter.commercelayer;

import com.coremedia.commerce.adapter.commercelayer.configuration.CommerceLayerApiConfigurationProperties;
import com.coremedia.commerce.adapter.commercelayer.configuration.CommerceLayerConnectorConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

public abstract class AbstractCommerceLayerIT {

    @Configuration(proxyBeanMethods = false)
    @Import({
            RestTemplateAutoConfiguration.class,
            JacksonAutoConfiguration.class,
            CommerceLayerConnectorConfiguration.class
    })
    @EnableConfigurationProperties({
            CommerceLayerApiConfigurationProperties.class
    })
    @PropertySource("classpath:/test-defaults.properties")
    public static class TestConfig {
    }

}
