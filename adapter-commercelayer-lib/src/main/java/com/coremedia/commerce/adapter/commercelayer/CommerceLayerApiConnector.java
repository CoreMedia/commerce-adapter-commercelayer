package com.coremedia.commerce.adapter.commercelayer;

import com.coremedia.commerce.adapter.commercelayer.configuration.CommerceLayerApiConfigurationProperties;
import com.coremedia.commerce.adapter.commercelayer.oauth.CommerceLayerAuthenticator;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.invoke.MethodHandles;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

public class CommerceLayerApiConnector {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final String protocol;
    private final String host;
    private final String basePath;
    private final CommerceLayerAuthenticator authenticator;
    private final RestTemplate restTemplate;

    public CommerceLayerApiConnector(@NonNull CommerceLayerApiConfigurationProperties properties,
                                     @NonNull CommerceLayerAuthenticator authenticator,
                                     @NonNull RestTemplate restTemplate) {
        this.protocol = properties.getProtocol();
        this.host = properties.getHost();
        this.basePath = properties.getBasePath();
        this.authenticator = authenticator;
        this.restTemplate = restTemplate;
    }

    public <T> Optional<T> getResource(String resourcePath, ParameterizedTypeReference<T> responseType) {
        return getResource(resourcePath, responseType, Collections.emptyMap());
    }

    public <T> Optional<T> getResource(String resourcePath, ParameterizedTypeReference<T> responseType, Map<String, String> urlParams) {
        requireNonEmptyResourcePath(resourcePath);
        String url = buildRequestTemplateUrl(resourcePath, Collections.emptySet(), false);

        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            return Optional.ofNullable(responseEntity.getBody());

        } catch (HttpClientErrorException ex) {
            LOG.trace("Call to '{}' with params '{}' raised exception.", url, urlParams, ex);
            HttpStatus statusCode = ex.getStatusCode();
            switch (statusCode) {
                case NOT_FOUND: {
                    LOG.trace("Returning empty result.");
                    return Optional.empty();
                }
                case UNAUTHORIZED: {
                    LOG.info("Retrying with a new access token ...");
                    authenticator.refreshAccessToken();
                    ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(buildHttpHeaders()), responseType, urlParams);
                    return Optional.ofNullable(responseEntity.getBody());
                }
                default: {
                    throw new RuntimeException(
                            String.format("REST call to '%s' with params '%s' failed. Exception: %s", url, urlParams, ex.getMessage()), ex);
                }
            }
        }
    }

    @VisibleForTesting
    String buildRequestTemplateUrl(String resourcePath, Set<String> queryParamNames, boolean isPaginatedPageRequest) {
        UriComponentsBuilder uriBuilder;

        if (isPaginatedPageRequest) {
            uriBuilder = UriComponentsBuilder.fromUriString(resourcePath);
        } else {
            uriBuilder = UriComponentsBuilder.newInstance()
                    .scheme(protocol)
                    .host(host)
                    .path(basePath);

            // Append resource path.
            uriBuilder.path(resourcePath);
        }

        // Append template query parameters.
        getDefaultQueryParams().keySet().forEach(name -> uriBuilder.queryParam(name, "{" + name + "}"));
        queryParamNames.forEach(name -> uriBuilder.queryParam(name, "{" + name + "}"));

        return uriBuilder.build().toString();
    }

    private Map<String, String> mergeUrlParams(Map<String, String> pathParams, ListMultimap<String, String> queryParams) {
        HashMap<String, String> urlParams = new HashMap<>(pathParams);
        getDefaultQueryParams().forEach(urlParams::put);
        queryParams.forEach(urlParams::put);
        return urlParams;
    }

    private HttpHeaders buildHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    private static void requireNonEmptyResourcePath(String resourcePath) {
        checkArgument(StringUtils.isNotBlank(resourcePath), "Cannot request empty resource path.");
    }

    private ListMultimap<String, String> getDefaultQueryParams() {
        return ImmutableListMultimap.of();
    }

}
