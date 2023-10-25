package com.coremedia.commerce.adapter.commercelayer.api.resources;

import com.coremedia.commerce.adapter.commercelayer.CommerceLayerApiConnector;
import com.coremedia.commerce.adapter.commercelayer.api.entities.DataEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.DataListEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.SKU;
import com.coremedia.commerce.adapter.commercelayer.api.entities.ShippingCategory;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SKUResource {

    private static final String ID_PARAM = "id";

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

    public Optional<SKU> getSku(String id) {
        ParameterizedTypeReference<DataEntity<SKU>> responseType = new ParameterizedTypeReference<>() {};

        Optional<DataEntity<SKU>> responseEntity = connector.getResource("/skus/{id}", Map.of(ID_PARAM, id), responseType);
        return responseEntity.map(DataEntity::getData);
    }

    public Optional<ShippingCategory> getShippingCategoryForSku(String id) {
        ParameterizedTypeReference<DataEntity<ShippingCategory>> responseType = new ParameterizedTypeReference<>() {};
        Optional<DataEntity<ShippingCategory>> responseEntity = connector.getResource("/skus/{id}/shipping_category", Map.of(ID_PARAM, id), responseType);
        return responseEntity.map(DataEntity::getData);
    }

}
