package com.coremedia.commerce.adapter.commercelayer.api.resources;

import com.coremedia.commerce.adapter.commercelayer.CommerceLayerApiConnector;
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

public class ShippingCategoriesResource extends CommerceLayerApiResource {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public ShippingCategoriesResource(CommerceLayerApiConnector connector) {
        super(connector);
    }

    public List<ShippingCategory> listShippingCategories() {
        ParameterizedTypeReference<DataListEntity<ShippingCategory>> responseType = new ParameterizedTypeReference<>() {
        };
        Optional<DataListEntity<ShippingCategory>> responseEntity = getConnector().getResource("/shipping_categories", responseType);
        Optional<List<ShippingCategory>> shippingCategories = responseEntity.map(DataListEntity::getData);
        return shippingCategories.orElse(Collections.emptyList());
    }

    public Optional<ShippingCategory> getShippingCategory(String id) {
        ParameterizedTypeReference<ShippingCategory> responseType = new ParameterizedTypeReference<>() {
        };
        return getConnector().getResource("/shipping_categories/{id}", Map.of(ID_PARAM, id), responseType);
    }

    public List<SKU> getAssociatedSkus(String id) {
        ParameterizedTypeReference<DataListEntity<SKU>> responseType = new ParameterizedTypeReference<>() {
        };
        Optional<DataListEntity<SKU>> responseEntity = getConnector().getResource("/shipping_categories/{id}/skus", Map.of(ID_PARAM, id), responseType);
        Optional<List<SKU>> skus = responseEntity.map(DataListEntity::getData);
        return skus.orElse(Collections.emptyList());
    }

}
