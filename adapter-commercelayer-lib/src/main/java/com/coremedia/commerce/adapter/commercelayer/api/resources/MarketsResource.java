package com.coremedia.commerce.adapter.commercelayer.api.resources;

import com.coremedia.commerce.adapter.commercelayer.CommerceLayerApiConnector;
import com.coremedia.commerce.adapter.commercelayer.api.entities.DataEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.DataListEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.Market;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.yaml.snakeyaml.error.Mark;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MarketsResource{
    
    private CommerceLayerApiConnector connector;
    
    public MarketsResource(CommerceLayerApiConnector connector) {
        this.connector = connector;
    }
    
    public List<Market> listMarkets() {
        ParameterizedTypeReference<DataListEntity<Market>> responseType = new ParameterizedTypeReference<>() {
        };
        Optional<DataListEntity<Market>> responseEntity = connector.getResource("/markets", responseType);
        Optional<List<Market>> markets = responseEntity.map(DataListEntity::getData);
        return markets.orElse(Collections.emptyList());
        
        
    }
    public Optional<Market> getMarket(String id) {
        ParameterizedTypeReference<DataEntity<Market>> responseType = new ParameterizedTypeReference<>() {
        };
        Optional<DataEntity<Market>> responseEntity = connector.getResource("/markets/{id}", responseType);
        return responseEntity.map(DataEntity::getData);
    }
}
