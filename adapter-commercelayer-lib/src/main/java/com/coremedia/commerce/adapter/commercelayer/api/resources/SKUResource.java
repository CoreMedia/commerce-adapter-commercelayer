package com.coremedia.commerce.adapter.commercelayer.api.resources;

import com.coremedia.commerce.adapter.commercelayer.CommerceLayerApiConnector;
import com.coremedia.commerce.adapter.commercelayer.api.entities.DataListEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.SKU;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SKUResource {

    private CommerceLayerApiConnector connector;

    public SKUResource(CommerceLayerApiConnector connector) {
        this.connector = connector;
    }

    public List<SKU> listSKUs() {
        ParameterizedTypeReference<DataListEntity<SKU>> responseType = new ParameterizedTypeReference<>() {};
        Optional<DataListEntity<SKU>> responseEntity = connector.getResource("/skus", responseType);
        Optional<List<SKU>> skus = responseEntity.map(DataListEntity::getData);
        return  skus.orElse(Collections.emptyList());
    }

}
