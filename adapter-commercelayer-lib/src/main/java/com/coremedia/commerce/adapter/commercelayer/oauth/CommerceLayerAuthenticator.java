package com.coremedia.commerce.adapter.commercelayer.oauth;

import com.coremedia.commerce.adapter.commercelayer.configuration.CommerceLayerApiConfigurationProperties;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;

public class CommerceLayerAuthenticator implements ClientHttpRequestInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String OAUTH_TOKEN_PATH = "/oauth/token";

    private static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";

    private String clientId;
    private String clientSecret;

    private final RestTemplate restTemplate;

    private String tokenRequestUrl;

    private AccessToken token;

    public CommerceLayerAuthenticator(@NonNull CommerceLayerApiConfigurationProperties properties,
                                      @NonNull RestTemplateBuilder restTemplateBuilder) {
        this.clientId = properties.getClientId();
        this.clientSecret = properties.getClientSecret();
        this.restTemplate = restTemplateBuilder.build();
        this.tokenRequestUrl = buildAuthenticationUrl(properties);
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request,
                                        byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        LOG.info("{} - {}", request.getMethod().toString(), request.getURI().toString());
        request.getHeaders().setBearerAuth(getAccessToken().getValue());

        return execution.execute(request, body);
    }

    public AccessToken getAccessToken() {
        if (token == null) {
            token = requestNewToken();
        }
        return token;
    }

    public @Nullable AccessToken refreshAccessToken() {
        if (token == null) {
            return requestNewToken();
        } else {
            LOG.debug("Refreshing access token.");

            Map<String, String> payload = Map.of(
                    "grant_type", GRANT_TYPE_CLIENT_CREDENTIALS,
                    "refresh_token", token.getValue(),
                    "client_id", clientId,
                    "client_secret", clientSecret
            );

            return requestTokenInternal(payload);
        }
    }

    public @Nullable AccessToken requestNewToken() {
        LOG.debug("Requesting new access token.");

        Map<String, String> payload = Map.of(
                "grant_type", GRANT_TYPE_CLIENT_CREDENTIALS,
                "client_id", clientId,
                "client_secret", clientSecret
        );

        return requestTokenInternal(payload);
    }

    private @Nullable AccessToken requestTokenInternal(Map<String, String> requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<AccessToken> responseEntity = restTemplate.exchange(tokenRequestUrl, HttpMethod.POST, requestEntity, AccessToken.class);
            AccessToken token = responseEntity.getBody();
            LOG.debug("Fetched access token {}.", token);
            return token;

        } catch (HttpClientErrorException e) {
            LOG.error("Unable to fetch access token.", e);
            return null;
        }
    }

    private String buildAuthenticationUrl(@NonNull CommerceLayerApiConfigurationProperties properties) {
        return UriComponentsBuilder.newInstance()
                .scheme(properties.getProtocol())
                .host(properties.getHost())
                .path(OAUTH_TOKEN_PATH)
                .build().toString();
    }
}
