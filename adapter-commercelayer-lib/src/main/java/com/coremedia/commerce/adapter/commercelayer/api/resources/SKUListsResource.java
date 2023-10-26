package com.coremedia.commerce.adapter.commercelayer.api.resources;

import com.coremedia.commerce.adapter.commercelayer.CommerceLayerApiConnector;
import com.coremedia.commerce.adapter.commercelayer.api.entities.DataListEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.SKUList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SKUListsResource extends CommerceLayerApiResource {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public SKUListsResource(CommerceLayerApiConnector connector) {
        super(connector);
    }

    public List<SKUList> listSkuLists() {
        LOG.debug("Fetching SKU lists");
        ParameterizedTypeReference<DataListEntity<SKUList>> responseType = new ParameterizedTypeReference<>() {
        };
        Optional<DataListEntity<SKUList>> responseEntity = getConnector().getResource("/sku_lists", responseType);
        return responseEntity.map(DataListEntity::getData).orElse(Collections.emptyList());
    }

    public Optional<SKUList> getSkuList(String id) {
        LOG.debug("Fetching SKU list with id {}.", id);
        ParameterizedTypeReference<SKUList> responseType = new ParameterizedTypeReference<>() {
        };
        return getConnector().getResource("/sku_lists/{id}", Map.of(ID_PARAM, id), responseType);
    }
}
