package com.coremedia.commerce.adapter.commercelayer.api.resources;

import com.coremedia.commerce.adapter.commercelayer.CommerceLayerApiConnector;
import com.coremedia.commerce.adapter.commercelayer.api.entities.DataEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.DataListEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.Market;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MarketsResource extends CommerceLayerApiResource {

    public MarketsResource(CommerceLayerApiConnector connector) {
        super(connector);
    }

    public List<Market> listMarkets() {
        ParameterizedTypeReference<DataListEntity<Market>> responseType = new ParameterizedTypeReference<>() {
        };
        Optional<DataListEntity<Market>> responseEntity = getConnector().getResource("/markets", responseType);
        Optional<List<Market>> markets = responseEntity.map(DataListEntity::getData);
        return markets.orElse(Collections.emptyList());
    }

    public Optional<Market> getMarket(String id) {
        ParameterizedTypeReference<DataEntity<Market>> responseType = new ParameterizedTypeReference<>() {
        };
        Optional<DataEntity<Market>> responseEntity = getConnector().getResource("/markets/{id}", responseType);
        return responseEntity.map(DataEntity::getData);
    }
}
