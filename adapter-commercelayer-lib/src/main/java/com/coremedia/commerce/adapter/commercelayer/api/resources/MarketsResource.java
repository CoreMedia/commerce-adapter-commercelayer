package com.coremedia.commerce.adapter.commercelayer.api.resources;

import com.coremedia.commerce.adapter.commercelayer.CommerceLayerApiConnector;
import com.coremedia.commerce.adapter.commercelayer.api.entities.DataEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.PaginatedEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.Market;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MarketsResource extends CommerceLayerApiResource {

    public MarketsResource(CommerceLayerApiConnector connector) {
        super(connector);
    }

    public List<Market> listMarkets() {
        ParameterizedTypeReference<PaginatedEntity<Market>> responseType = new ParameterizedTypeReference<>() {
        };
        Optional<PaginatedEntity<Market>> responseEntity = getConnector().getResource("/markets", responseType);
        Optional<List<Market>> markets = responseEntity.map(PaginatedEntity::getData);
        return markets.orElse(Collections.emptyList());
    }

    public Optional<Market> getMarket(String id) {
        ParameterizedTypeReference<DataEntity<Market>> responseType = new ParameterizedTypeReference<>() {
        };
        Optional<DataEntity<Market>> responseEntity = getConnector().getResource("/markets/{id}", Map.of(ID_PARAM, id), responseType);
        return responseEntity.map(DataEntity::getData);
    }
    public Optional<Market> searchMarket(String name) {
        Optional<Market> result = Optional.empty();
        for (Market m: listMarkets()) {
            if (m.getAttributes().getName().equals(name)) {
                result = Optional.of(m);
            }
        
        }
        return result;
    
    }
}
