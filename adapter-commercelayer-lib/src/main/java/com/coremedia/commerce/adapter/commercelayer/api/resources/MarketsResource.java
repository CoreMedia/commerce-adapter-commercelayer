package com.coremedia.commerce.adapter.commercelayer.api.resources;

import com.coremedia.commerce.adapter.commercelayer.CommerceLayerApiConnector;
import com.coremedia.commerce.adapter.commercelayer.api.entities.DataEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.PaginatedEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.Market;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;

import java.lang.invoke.MethodHandles;
import java.util.*;

public class MarketsResource extends CommerceLayerApiResource {

  private static final String MARKETS_PATH = "/markets";

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  public MarketsResource(CommerceLayerApiConnector connector) {
    super(connector);
  }

  public List<Market> listMarkets() {
    LOG.debug("Fetching all markets");
    ParameterizedTypeReference<PaginatedEntity<Market>> responseType = new ParameterizedTypeReference<>() {
    };
    return fetchAllPages(MARKETS_PATH, responseType);
  }

  public Optional<Market> getMarket(String id) {
    ParameterizedTypeReference<DataEntity<Market>> responseType = new ParameterizedTypeReference<>() {
    };
    Optional<DataEntity<Market>> responseEntity = getConnector().getResource("/markets/{id}", Map.of(ID_PARAM, id), responseType);
    return responseEntity.map(DataEntity::getData);
  }

  public Optional<Market> searchMarket(String name) {
    Optional<Market> result = Optional.empty();
    for (Market m : listMarkets()) {
      if (m.getAttributes().getName().equals(name)) {
        result = Optional.of(m);
      }

    }
    return result;

  }
}
