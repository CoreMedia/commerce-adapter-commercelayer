package com.coremedia.commerce.adapter.commercelayer.api.resources;

import com.coremedia.commerce.adapter.commercelayer.CommerceLayerApiConnector;
import com.coremedia.commerce.adapter.commercelayer.api.entities.DataEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.DataListEntity;
import com.coremedia.commerce.adapter.commercelayer.api.entities.SKU;
import com.coremedia.commerce.adapter.commercelayer.api.entities.ShippingCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SKUResource extends CommerceLayerApiResource {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public SKUResource(CommerceLayerApiConnector connector) {
        super(connector);
    }

    public List<SKU> listSKUs() {
        ParameterizedTypeReference<DataListEntity<SKU>> responseType = new ParameterizedTypeReference<>() {};
        Optional<DataListEntity<SKU>> responseEntity = getConnector().getResource("/skus", responseType);
        Optional<List<SKU>> skus = responseEntity.map(DataListEntity::getData);
        return  skus.orElse(Collections.emptyList());
    }

    public Optional<SKU> getSku(String id) {
        ParameterizedTypeReference<DataEntity<SKU>> responseType = new ParameterizedTypeReference<>() {};

        Optional<DataEntity<SKU>> responseEntity = getConnector().getResource("/skus/{id}", Map.of(ID_PARAM, id), responseType);
        return responseEntity.map(DataEntity::getData);
    }

    public Optional<ShippingCategory> getShippingCategoryForSku(String id) {
        ParameterizedTypeReference<DataEntity<ShippingCategory>> responseType = new ParameterizedTypeReference<>() {};
        Optional<DataEntity<ShippingCategory>> responseEntity = getConnector().getResource("/skus/{id}/shipping_category", Map.of(ID_PARAM, id), responseType);
        return responseEntity.map(DataEntity::getData);
    }

}
