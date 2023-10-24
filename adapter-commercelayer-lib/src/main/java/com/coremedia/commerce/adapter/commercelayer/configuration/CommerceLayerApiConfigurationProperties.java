package com.coremedia.commerce.adapter.commercelayer.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "commercelayer.api")
public class CommerceLayerApiConfigurationProperties {

    /**
     * Protocol for REST API communication.
     */
    private String protocol = "https";

    /**
     * Full qualified hostname of the Commerce Layer instance.
     */
    private String host = "yourdomain.commercelayer.io";

    /**
     * REST API base path.
     */
    private String basePath = "/api";

    /**
     * Client id.
     */
    private String clientId;

    /**
     * Client secret.
     */
    private String clientSecret;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
